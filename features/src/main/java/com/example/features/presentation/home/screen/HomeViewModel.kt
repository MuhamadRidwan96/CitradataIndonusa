package com.example.features.presentation.home.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.data.local.toDomain
import com.example.data.repositoryImpl.FilterDataRepositoryImpl
import com.example.data.utils.Constant
import com.example.data.utils.TokenExpiredException
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
import com.example.features.presentation.home.state.HomeUiState
import com.example.features.presentation.home.utils.toFilterDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    filteredUseCase: FilteredUseCase,
    profileUseCase: ProfileUseCase,
    private val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    private val insertFavorite: InsertFavoriteUseCase,
    private val deleteFavorite: DeleteFavoriteUseCase,
    repository: FilterDataRepository,
    private val logoutUseCase: LogoutUseCase
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
    val favoriteProjects: StateFlow<List<FavoriteProject>> = _favoriteProjects

    var userProfile by mutableStateOf<UserProfile?>(null)

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized = _isInitialized.asStateFlow()


    init {
        observeFavorites()
        viewModelScope.launch {
            delay(1500)
            _isInitialized.value = true
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavoriteUseCase().collect { fav ->
                _favoriteProjects.value = fav
            }
        }
    }

    fun toggleFavorite(project: FavoriteProjectEntity) {
        viewModelScope.launch(Dispatchers.IO) {
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
                DataEvent.ShowSnackBar(e.message ?: Constant.UNKNOWN_ERROR)
            }
        }
    }

    init {
        viewModelScope.launch {
            profileUseCase().collect { profile ->
                userProfile = profile
            }
        }
    }

    init {
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

    fun applyProjectName(names: Map<String, String>) {
        _searchQuery.value = if (names.isEmpty()) emptyMap() else names
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

    fun onLogoutClicked() {
        viewModelScope.launch(Dispatchers.Default) {
            logoutUseCase()
        }
    }

    fun refreshPaging(){
        _searchQuery.value = _searchQuery.value.toMap()
    }
}

sealed class DataEvent {
    data object Success : DataEvent()
    data class ShowSnackBar(val message: String) : DataEvent()
}