package com.example.features.presentation.home.screen


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.data.local.mapToDonut
import com.example.data.repositoryImpl.FilterDataRepositoryImpl
import com.example.domain.model.StatisticProvince
import com.example.domain.repository.FilterDataRepository
import com.example.domain.usecase.authentication.LogoutUseCase
import com.example.domain.usecase.authentication.ProfileUseCase
import com.example.domain.usecase.notification.ObserveUnreadCountUseCase
import com.example.domain.usecase.statistic.StatisticUseCase
import com.example.features.presentation.home.state.dashboard.DashboardUiAction
import com.example.features.presentation.home.state.dashboard.DashboardUiEvent
import com.example.features.presentation.home.state.dashboard.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(

    private val profileUseCase: ProfileUseCase,
    private val repository: FilterDataRepository,
    private val logoutUseCase: LogoutUseCase,
    private val statisticUseCase: StatisticUseCase,
    observeUnreadCountUseCase: ObserveUnreadCountUseCase
) : BaseViewModel<
        DashboardUiState, DashboardUiEvent
        >(initialState = DashboardUiState()),
    ActionHandler<DashboardUiAction> {


    val unread = observeUnreadCountUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )
    init {
        action(DashboardUiAction.OnRefresh)
    }

    override fun action(action: DashboardUiAction) {
        when (action) {

            DashboardUiAction.OnRefresh -> {
                refresh()
            }

            DashboardUiAction.OnRetry -> {
                fetchStatistic(force = true)
            }

            is DashboardUiAction.OnSearchQueryChanged -> {
                handleSearchQuery(action.query)
            }

            DashboardUiAction.OnToggleProvince -> {
                toggleProvince()
            }

            DashboardUiAction.OnNotificationClick -> {
                sendEvent(
                    DashboardUiEvent.NavigateToNotification
                )
            }

            DashboardUiAction.OnLogoutClicked -> {
                logout()
            }

            is DashboardUiAction.OnCategoryClick -> {
                sendEvent(
                    DashboardUiEvent.NavigateToProjectByCategory(
                        cat = action.category
                    )
                )
            }

            is DashboardUiAction.OnStatusClick -> {
                sendEvent(
                    DashboardUiEvent.NavigateToProjectByStatus(
                        status = action.status
                    )
                )
            }

            is DashboardUiAction.OnCityClick -> {
                sendEvent(
                    DashboardUiEvent.NavigateToProjectByCity(
                        city = action.city
                    )
                )
            }
        }
    }

    init {
        setUpTokenExpired()
    }


    private fun refresh() {
        observeProfile()
        fetchStatistic(force = true)
    }

    private fun setUpTokenExpired() {
        if (repository is FilterDataRepositoryImpl) {
            repository.onTokenExpiredCallBack = {
                sendEvent(
                    DashboardUiEvent.Error(
                        message = "Session Expired"
                    )
                )
            }
            repository.onDataNotFoundCallBack = {
                sendEvent(
                    DashboardUiEvent.Error(
                        message = "Data Not Founf"
                    )
                )
            }
        }
    }

    private fun observeProfile() {
        viewModelScope.launch {
            profileUseCase().collect { profile ->
                reduce {
                    copy(
                        user = profile
                    )
                }
            }
        }
    }

    private fun handleSearchQuery(query: String) {
        // Implement search jika dashboard
        // memang membutuhkan filtering/search.
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            sendEvent(DashboardUiEvent.Logout)
        }
    }

    //Masih bug,perlu perbaikan
    private fun fetchStatistic(force: Boolean = false) {
        viewModelScope.launch {

            // Jangan request ulang jika data sudah tersedia,
            // kecuali memang sedang melakukan refresh.
            if (!force && (uiState.value.isLoading || uiState.value.isLoaded)) {
                return@launch
            }
            reduce {
                copy(
                    isLoading = true, error = null
                )
            }

            val refresh = currentState.isInitialized
            reduce {
                copy(
                    isLoading = !refresh, isRefresh = refresh, error = null
                )
            }
            statisticUseCase().fold(

                onSuccess = { response ->
                    val statistic = response.statistics
                    val dashboard = response.dashboard

                    val provinceData = response.statistics.byProvince.map { (province, total) ->
                        StatisticProvince(
                            province = province, total = total
                        )
                    }.sortedByDescending { it.total }.toImmutableList()

                    val byStatus = mapToDonut(
                        statistic.byStatus, CATEGORY_COLORS
                    )

                    val total = statistic.totalProjects.takeIf { it > 0 } ?: 1

                    val categoryTrend = statistic.byCategory.mapValues { (_, count) ->
                        (count * 100f / total).roundToInt()

                    }.toImmutableMap()

                    reduce {
                        copy(
                            isLoading = false,
                            isLoaded = true,
                            isRefresh = false,
                            isInitialized = true,
                            error = null,

                            totalProjects = statistic.totalProjects,

                            byCategory = statistic.byCategory.toImmutableMap(),
                            byStatus = byStatus,
                            byProvince = provinceData,
                            categoryTrend = categoryTrend,
                            dashboard = dashboard.trend.toImmutableList()
                        )
                    }

                }, onFailure = { exception ->
                    reduce {
                        copy(
                            isLoading = false,
                            isRefresh = false,
                            error = exception.message ?: "Failed to fetch statistic"
                        )
                    }
                })
        }
    }

    private companion object {
        val CATEGORY_COLORS = mapOf(
            "UNDER CONSTRUCTION" to Color(0xFFF0A857),
            "PLANNING" to Color(0xFF6C93C7),
            "POST TENDER" to Color(0xFF9B85C4),
            "PILLING WORK" to Color(0xFF4FB199),
            "HOLD PROJECT" to Color(0xFFE2726F)// Soft Pastel Orange
        )
    }

    private fun toggleProvince() = viewModelScope.launch {

        reduce {
            copy(
                isShowAll = !isShowAll
            )
        }
    }

}

/*

    fun applyProjectName(names: Map<String, String>) {
        _searchQuery.value = if (names.isEmpty()) emptyMap() else names
    }
*/


/*private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val insertFavorite: InsertFavoriteUseCase,
    filteredUseCase: FilteredUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,*/

/* private val _favoriteProjects = MutableStateFlow<List<FavoriteProject>>(emptyList())
    val favoriteProjects = _favoriteProjects.asStateFlow()*/
//private val _searchCategory = MutableStateFlow<Map<String, String>>(emptyMap())

/*val currentPagingData: Flow<PagingData<RecordData>> =
       combine(
           _searchQuery.debounce(300).distinctUntilChanged(),
           _searchCategory.debounce(50).distinctUntilChanged()
       ) { query, category ->
           query to category
       }
           .flatMapLatest { (query, category) ->
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
*/

/* private fun observeFavorites() {
         viewModelScope.launch(dispatcher) {
             getAllFavoriteUseCase().collect { fav ->
                 _favoriteProjects.value = fav
             }
         }
     }

     //Bug!! Send few request on Snack bar
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
     }*/