package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.toDomain
import com.example.data.utils.Constant
import com.example.data.utils.DataNotFoundException
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.FavoriteProject
import com.example.domain.response.RecordData
import com.example.domain.usecase.authentication.LogoutUseCase
import com.example.domain.usecase.data.FilteredUseCase
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.domain.usecase.room.InsertFavoriteUseCase
import com.example.features.presentation.home.utils.toFilterDataModel
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.state.SearchBottomSheetAction
import com.example.features.presentation.search.state.hasFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel yang mengelola data pencarian dengan berbagai parameter
 * Menggunakan single ProjectFilterState sebagai sumber kebenaran (SSOT),
 * sehingga UI dapat mengamati state secara terpadu.
 *
 * ViewModel ini mendukung:
 * - Caching province & city agar tidak fetch ulang
 * - SharedFlow event untuk one-time event (snackbar, toast)
 * - StateFlow untuk UI state yang stabil
 */


@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filterDataUseCase: FilteredUseCase,
    private val favoriteUseCase: GetAllFavoriteUseCase,
    private val insertFavorite: InsertFavoriteUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    /**
     * State utama untuk UI.
     * Berisi data province, city, start date, end date, category project dll.
     */
    private val _appliedState = MutableStateFlow(ProjectFilterState())
    val appliedState = _appliedState.asStateFlow()

    /** ✅ State sementara yang dipakai di bottom sheet
     * Berisi data province, city, start date, end date, category project dll.
     */
    private val _draftState = MutableStateFlow(ProjectFilterState())
    val draftState = _draftState.asStateFlow()

    /** Status pencarian */
    private val _hasSearched = MutableStateFlow(false)
    val hasSearched = _hasSearched.asStateFlow()

    /** Event token expired*/
    private val _tokenExpired = MutableSharedFlow<Unit>()

    /** Event data not found or empty data */
    private val _dataNotFound = MutableSharedFlow<Unit>()

    /** Event data (Snack bar, toast ,dll) */
    private val _dataEvent = Channel<DataEvent>(Channel.BUFFERED)
    val dataEvent = _dataEvent.receiveAsFlow()

    /** Data Favorite */
    private val _favorite = MutableStateFlow<List<FavoriteProject>>(emptyList())
    val favorite: StateFlow<List<FavoriteProject>> = _favorite

    /** Initialized */
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized = _isInitialized.asStateFlow()


    init {
        observeFavorite() // Start observation favorite
        viewModelScope.launch {
            _isInitialized.value = true
        }
    }

    /** Observation favorite item*/
    private fun observeFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase().collect { fav ->
                _favorite.value = fav
            }
        }
    }

    /** Handle filter action*/
    fun onAction(action: SearchBottomSheetAction) {
        when (action) {
            is SearchBottomSheetAction.SetWithPpr -> {
                updateDraft {
                    it.copy(
                        withPpr = action.value,
                        ppr = if (action.value) "PPR" else ""
                    )
                }
            }

            is SearchBottomSheetAction.SetStartDate -> {
                updateDraft {
                    it.copy(
                        startDate = action.date
                    )
                }
            }

            is SearchBottomSheetAction.SetEndDate -> {
                updateDraft {
                    it.copy(
                        endDate = action.date
                    )
                }
            }

            is SearchBottomSheetAction.ClearStartDate -> {
                updateDraft {
                    it.copy(
                        startDate = ""
                    )
                }
            }

            is SearchBottomSheetAction.ClearEndDate -> {
                updateDraft {
                    it.copy(
                        endDate = ""
                    )
                }
            }

            is SearchBottomSheetAction.SelectStatus -> {
                updateDraft {
                    it.copy(
                        idProjectStatusCategory = action.id,
                        statusCategory = action.name
                    )
                }
            }

            is SearchBottomSheetAction.SelectBuildingCategory -> {
                    updateDraft {
                        it.copy(
                            idBuildingCategory = action.id,
                            buildingCategoryName = action.name
                        )
                    }
            }

            is SearchBottomSheetAction.SelectProjectCategory -> {
                updateDraft {
                    it.copy(
                        idProjectCategory = action.id,
                        projectCategoryName = action.name
                    )
                }
            }

            is SearchBottomSheetAction.QueryChange -> {
                updateDraft { it.copy(
                    query = action.query
                ) }
            }
            is SearchBottomSheetAction.SelectProvince -> {
                updateDraft { it.copy(
                    idProvince = action.id,
                    provinceName = action.name,
                    idCity = null,
                    cityName = ""
                )}
            }
            is SearchBottomSheetAction.SelectCity -> {
                updateDraft { it.copy(
                    idCity = action.id,
                    idProvince = action.provinceId,
                    cityName = action.name
                ) }
            }

            is SearchBottomSheetAction.Apply -> {
                applyFilters()
            }

            else -> Unit
        }
    }

    /** Handle favorite action*/
    fun toggleFavorite(fav: FavoriteProjectEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favorite = _favorite.value.any { it.idProject == fav.idProject }
                if (favorite) {
                    deleteFavorite(fav.idProject)
                    _dataEvent.send(DataEvent.ShowSnackBar("Dihapus dari favorite"))

                } else {
                    insertFavorite(fav.toDomain())
                    _dataEvent.send(DataEvent.ShowSnackBar("Ditambahkan ke favorit"))
                }

            } catch (e: Exception) {
                _dataEvent.send(DataEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR))
            }
        }
    }

    /** Observe paging data*/
    val dataPaging: Flow<PagingData<RecordData>> =
        _appliedState.debounce(300).distinctUntilChanged()
            .flatMapLatest { state ->

                if (state.hasFilter()) {
                    filterDataUseCase(
                        filterData = state.toFilterDataModel()
                    )
                } else {
                    flowOf(PagingData.empty())
                }

            }.catch { e ->
                when (e) {
                    is TokenExpiredException -> _tokenExpired.emit(Unit)
                    is DataNotFoundException -> {
                        // Handle khusus untuk data not found
                        _dataNotFound.emit(Unit)
                    }

                    is Exception -> _dataEvent.send(
                        DataEvent.ShowSnackBar(
                            e.message ?: Constant.UNKNOWN_ERROR
                        )
                    )

                    else -> throw e
                }
            }.cachedIn(viewModelScope)


    /**dipanggil ketika user update filter di bottom sheet*/
    fun updateDraft(block: (ProjectFilterState) -> ProjectFilterState) {
        _draftState.update(block)
    }

    /** dipanggil ketika user tekan tombol Cari*/
    fun applyFilters() {
        _appliedState.value = _draftState.value
        _hasSearched.value = true
    }

    /** Clear date range state*/
    fun clearDateRange() {
        _appliedState.update {
            it.copy(
                startDate = "",
                endDate = ""
            )
        }
        _draftState.update {
            it.copy(
                startDate = "",
                endDate = ""
            )
        }
    }
    /** Clear Category state*/
    fun clearCategory() {
        _appliedState.update { it.copy(idProjectCategory = null, projectCategoryName = "") }
        _draftState.update { it.copy(idProjectCategory = null, projectCategoryName = "") }
    }
    /** Clear PPR state*/
    fun clearPpr() {
        _appliedState.update { it.copy(withPpr = false, ppr = "") }
        _draftState.update { it.copy(withPpr = false, ppr = "") }
    }
    /** Clear province state*/
    fun clearProvince() {
        _appliedState.update { it.copy(idProvince = null, provinceName = "") }
        _draftState.update { it.copy(idProvince = null, provinceName = "") }
    }
    /** Clear City state*/
    fun clearCity() {
        _appliedState.update { it.copy(idCity = null, cityName = "") }
        _draftState.update { it.copy(idCity = null, cityName = "") }
    }
    /** Clear status state*/
    fun clearStatus() {
        _appliedState.update {
            it.copy(
                idProjectStatusCategory = null,
                statusCategory = ""
            )
        }
        _draftState.update { it.copy(idProjectStatusCategory = null, statusCategory = "") }
    }
    /** Clear building state*/
    fun clearBuilding() {
        _appliedState.update {
            it.copy(
                idBuildingCategory = null,
                buildingCategoryName = ""
            )
        }
        _draftState.update { it.copy(idBuildingCategory = null, buildingCategoryName = "") }
    }
    /** Handle action logout*/
    fun onLogoutClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
        }
    }
}

sealed class DataEvent {
    data object Success : DataEvent()
    data class ShowSnackBar(val message: String) : DataEvent()
}
