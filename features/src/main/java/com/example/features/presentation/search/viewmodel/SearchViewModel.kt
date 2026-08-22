package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.toDomain
import com.example.data.utils.Constant
import com.example.data.utils.DataNotFoundException
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.Project
import com.example.domain.usecase.authentication.LogoutUseCase
import com.example.domain.usecase.data.FilteredUseCase
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.domain.usecase.room.InsertFavoriteUseCase
import com.example.features.presentation.home.utils.toFilterDataModel
import com.example.features.presentation.search.state.search.SearchUiAction
import com.example.features.presentation.search.state.search.SearchUiEvent
import com.example.features.presentation.search.state.search.SearchUiState
import com.example.features.presentation.search.utils.hasFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
) : BaseViewModel<
        SearchUiState,
        SearchUiEvent
        >(initialState = SearchUiState()), ActionHandler<SearchUiAction> {

    init {
        observeFavorite()
        reduce {
            copy(
                isInitialized = true
            )
        }
    }

    /**
     **=========================================================**
     *
     *ACTION
     **=========================================================**/

    override fun action(action: SearchUiAction) {
        when (action) {

            /** -------------------------
            Search
             **  ------------------------- **/
            is SearchUiAction.QueryChanged -> {
                reduce {
                    copy(
                        queryChange = action.query,
                        draftFilter = draftFilter.copy(
                            projectName = action.query
                        ),
                        appliedFilter = draftFilter.copy(
                            projectName = action.query
                        ),
                        hasSearched = true
                    )
                }
            }

            SearchUiAction.ApplyFilter -> {
                reduce {
                    copy(
                        appliedFilter = draftFilter,
                        hasSearched = true
                    )
                }
            }

            SearchUiAction.Dismiss ->{
                reduce {
                    copy(

                    )
                }
            }

            is SearchUiAction.SetWithPpr -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            withPpr = action.value,
                            ppr = if (action.value) "PPR" else ""
                        )
                    )
                }
            }

            is SearchUiAction.SetStartDate -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            startDate = action.startDate
                        )
                    )
                }
            }

            is SearchUiAction.SetEndDate -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            endDate = action.endDate
                        )
                    )
                }
            }

            is SearchUiAction.AddressChanged -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            address = action.address
                        )
                    )
                }
            }

            is SearchUiAction.SetProvince -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            idProvince = action.id,
                            provinceName = action.name,
                            idCity = null,
                            cityName = ""
                        )
                    )
                }

            }

            is SearchUiAction.SetCity -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            idCity = action.id,
                            idProvince = action.provinceId,
                            cityName = action.name
                        )
                    )
                }
            }

            is SearchUiAction.SetBuildingCategory -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            idBuildingCategory = action.id,
                            buildingCategoryName = action.name
                        )
                    )
                }
            }

            is SearchUiAction.SetProjectCategory -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            idProjectCategory = action.id,
                            projectCategoryName = action.name

                        )
                    )
                }
            }

            is SearchUiAction.SetStatus -> {
                reduce {
                    copy(
                        draftFilter = draftFilter.copy(
                            idProjectStatusCategory = action.id,
                            statusCategory = action.name
                        )
                    )
                }
            }

            is SearchUiAction.ClearPpr -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            withPpr = false,
                            ppr = ""
                        ),
                        draftFilter = draftFilter.copy(
                            withPpr = false,
                            ppr = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearDateRange -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            startDate = "",
                            endDate = ""
                        ),
                        draftFilter = draftFilter.copy(
                            startDate = "",
                            endDate = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearProvince -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            idProvince = null,
                            provinceName = ""
                        ),
                        draftFilter = draftFilter.copy(
                            idProvince = null,
                            provinceName = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearCity -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            idCity = null,
                            cityName = ""
                        ),
                        draftFilter = draftFilter.copy(
                            idCity = null,
                            cityName = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearBuilding -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            idBuildingCategory = null,
                            buildingCategoryName = ""
                        ),
                        draftFilter = draftFilter.copy(
                            idBuildingCategory = null,
                            buildingCategoryName = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearCategory -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            idProjectCategory = null,
                            projectCategoryName = ""
                        ),
                        draftFilter = draftFilter.copy(
                            idProjectCategory = null,
                            projectCategoryName = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearStatus -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            idProjectStatusCategory = null,
                            statusCategory = ""
                        ),
                        draftFilter = draftFilter.copy(
                            idProjectStatusCategory = null,
                            statusCategory = ""
                        )
                    )
                }
            }

            is SearchUiAction.ClearAddress -> {
                reduce {
                    copy(
                        appliedFilter = appliedFilter.copy(
                            address = ""
                        ),
                        draftFilter = draftFilter.copy(
                            address = ""
                        )
                    )
                }
            }

            is SearchUiAction.ToggleFavorite -> {
                toggleFavorite(action.favorite)
            }

            is SearchUiAction.PagingError -> {
                handlePagingError(action.throwable)
            }

            is SearchUiAction.LogoutClicked -> {
                logout()
            }
        }
    }

    /** Observation favorite item*/
    private fun observeFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase().collect { fav ->
                reduce {
                    copy(
                        favorites = fav.toImmutableList()
                    )
                }
            }
        }
    }

    val dataPaging: Flow<PagingData<Project>> =
        uiState.map {
            it.appliedFilter
        }
            .debounce(1000)
            .distinctUntilChanged()
            .flatMapLatest { filter ->
                if (filter.hasFilter()) {
                    filterDataUseCase(
                        filterData = filter.toFilterDataModel()
                    )
                } else {
                    flowOf(PagingData.empty<Project>())
                }
            }


    // =========================================================
    // FAVORITE
    // =========================================================

    fun toggleFavorite(favorite: FavoriteProjectEntity) {
        val exists = uiState.value.favorites.any {
            it.idProject == favorite.idProject
        }

        viewModelScope.launch(Dispatchers.IO) {

            try {

                if (exists) {
                    deleteFavorite(favorite.idProject)
                    sendEvent(SearchUiEvent.ShowSnackBar("Dihapus dari favorite"))

                } else {
                    insertFavorite(favorite.toDomain())
                    sendEvent(SearchUiEvent.ShowSnackBar("Ditambahkan ke favorit"))
                }

            } catch (e: Exception) {
                sendEvent(SearchUiEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR))
            }
        }

    }

    // =========================================================
    // PAGING ERROR
    // =========================================================

    private fun handlePagingError(throwable: Throwable) {
        when (throwable) {
            is TokenExpiredException -> {
                sendEvent(
                    SearchUiEvent.TokenExpired
                )
            }

            is DataNotFoundException -> {

            }

            else -> {
                sendEvent(
                    SearchUiEvent.ShowSnackBar(
                        throwable.message ?: Constant.UNKNOWN_ERROR
                    )
                )
            }
        }
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    fun logout() {
        viewModelScope.launch {
            try {
                logoutUseCase()

                sendEvent(
                    SearchUiEvent.Logout
                )

            } catch (e: Exception) {
                sendEvent(SearchUiEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR))
            }
        }
    }
}