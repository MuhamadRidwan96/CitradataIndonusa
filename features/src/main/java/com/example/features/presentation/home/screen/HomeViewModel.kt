package com.example.features.presentation.home.screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.Result
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.mapToDonut
import com.example.data.local.toDomain
import com.example.data.repositoryImpl.FilterDataRepositoryImpl
import com.example.data.utils.Constant
import com.example.data.utils.TokenExpiredException
import com.example.domain.di.IoDispatcher
import com.example.domain.model.DonutData
import com.example.domain.model.FavoriteProject
import com.example.domain.model.UserProfile
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import com.example.domain.usecase.authentication.LogoutUseCase
import com.example.domain.usecase.authentication.ProfileUseCase
import com.example.domain.usecase.data.FilteredUseCase
import com.example.domain.usecase.room.DeleteFavoriteUseCase
import com.example.domain.usecase.room.GetAllFavoriteUseCase
import com.example.domain.usecase.room.InsertFavoriteUseCase
import com.example.domain.usecase.statistic.StatisticUseCase
import com.example.features.presentation.home.state.HomeUiState
import com.example.features.presentation.home.state.StatisticsDataState
import com.example.features.presentation.home.utils.toFilterDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    filteredUseCase: FilteredUseCase,
    private val profileUseCase: ProfileUseCase,
    private val statisticUseCase: StatisticUseCase,
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val insertFavorite: InsertFavoriteUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
    private val repository: FilterDataRepository,
    private val logoutUseCase: LogoutUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _tokenExpired = MutableSharedFlow<Unit>()

    private val _dataNotFound = MutableSharedFlow<Unit>()

    private val _searchQuery = MutableStateFlow<Map<String, String>>(emptyMap())
    val search = _searchQuery.asStateFlow()

    private val _searchCategory = MutableStateFlow<Map<String, String>>(emptyMap())

    private val _dataEvent = Channel<DataEvent>(Channel.BUFFERED)
    val dataEvent = _dataEvent.receiveAsFlow()

    private val _favoriteProjects = MutableStateFlow<List<FavoriteProject>>(emptyList())
    val favoriteProjects = _favoriteProjects.asStateFlow()

    private val _userName = MutableStateFlow<UserProfile?>(null)
    val userName = _userName.asStateFlow()

    private val _statisticState = MutableStateFlow(StatisticsDataState())
    val statisticState = _statisticState.asStateFlow()

    private val _byStatus = MutableStateFlow<ImmutableList<DonutData>>(persistentListOf())
    val byStatus = _byStatus.asStateFlow()





    init {
        setUpTokenExpired()

        viewModelScope.launch {
            supervisorScope {
                val statisticJob = async(dispatcher) { fetchStatistic() }
                val profileJob = async(dispatcher) { fetchProfile() }

                statisticJob.await()
                profileJob.await()

                launch(dispatcher) { observeFavorites() }
            }
        }
    }


    private fun setUpTokenExpired() {
        if (repository is FilterDataRepositoryImpl) {
            repository.onTokenExpiredCallBack = {
                viewModelScope.launch {
                    _tokenExpired.tryEmit(Unit)
                }
            }
            repository.onDataNotFoundCallBack = {
                viewModelScope.launch {
                    _dataNotFound.tryEmit(Unit)
                }
            }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch(dispatcher) {
            profileUseCase().collect { profile ->
                _userName.value = profile
            }
        }
    }

    val currentPagingData: Flow<PagingData<RecordData>> =
        combine(
            _searchQuery.debounce(300).distinctUntilChanged(),
            _searchCategory.debounce(50).distinctUntilChanged()
        ) { query, category ->
            query to category
        }.flatMapLatest { (query, category) ->
            val merge = query + category
            filteredUseCase(filterData = merge.toFilterDataModel())
        }.catch { e ->
            handleError(e)
        }.cachedIn(viewModelScope)


    private fun handleError(e: Throwable) {
        viewModelScope.launch {
            when (e) {
                is TokenExpiredException -> _tokenExpired.emit(Unit)
                else -> _dataEvent.send(
                    DataEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR)
                )
            }
        }
    }


    private fun observeFavorites() {
        viewModelScope.launch(dispatcher) {
            getAllFavoriteUseCase().collect { fav ->
                _favoriteProjects.value = fav
            }
        }
    }

    //Bug!! Send few request on snackbar
    fun toggleFavorite(project: FavoriteProjectEntity) {
        viewModelScope.launch {
            try {
                val favorite = _favoriteProjects.value.any { it.idProject == project.idProject }
                if (favorite) {
                    deleteFavorite(project.idProject)
                    _dataEvent.send(DataEvent.ShowSnackBar("Favorit berhasil dihapus  "))

                } else {
                    insertFavorite(project.toDomain())
                    _dataEvent.send(DataEvent.ShowSnackBar("Ditambahkan ke favorit"))
                }
            } catch (e: Exception) {
                _dataEvent.send(
                    DataEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR)
                )

            }
        }
    }


    fun applyProjectName(names: Map<String, String>) {
        _searchQuery.value = if (names.isEmpty()) emptyMap() else names
    }


    fun onLogoutClicked() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }


    fun fetchStatistic() {
        viewModelScope.launch {
            if (_statisticState.value.isLoading || _statisticState.value.totalProjects > 0) return@launch // ✅ cegah re-request

            _statisticState.value = _statisticState.value.copy(isLoading = true)

            statisticUseCase()
                .catch { e ->
                    _statisticState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to fetch statistic"
                        )
                    }
                }.collect { result ->
                    when (result) {

                        is Result.Success -> {
                            val data = result.data.data

                            val categoryColors = mapOf(
                                "UNDER CONSTRUCTION" to Color(0xFFE74C3C),
                                "PLANNING" to Color(0xFF27AE60),
                                "POST TENDER" to Color(0xFFF1C40F),
                                "PILLING WORK" to Color(0xFF1ABC9C),
                                "HOLD PROJECT" to Color(0xFFFB8C00)
                            )

                            val total = data.totalProjects.takeIf { it > 0 } ?: 1

                            val trends = withContext(Dispatchers.Default) {
                                data.byCategory.mapValues { (_, count) ->
                                    ((count.toFloat() / total.toFloat()) * 100f).roundToInt()
                                }
                            }

                            _byStatus.value = mapToDonut(data.byStatus, categoryColors)
                            _statisticState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoaded = true,
                                    totalProjects = data.totalProjects,
                                    byCategory = data.byCategory,
                                    byStatus = data.byStatus,
                                    byProvince = data.byProvince,
                                    categoryTrends = trends,

                                    )
                            }
                        }

                        is Result.Error -> {
                            _statisticState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoaded = false,
                                    error = result.exception.message ?: "Unknown Error!"
                                )
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }
}

sealed class DataEvent {
    data object Success : DataEvent()
    data class ShowSnackBar(val message: String) : DataEvent()
}