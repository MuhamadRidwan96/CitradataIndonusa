package com.example.features.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repositoryImpl.FilterDataRepositoryImpl
import com.example.data.utils.Constant
import com.example.data.utils.TokenExpiredException
import com.example.domain.model.UserProfile
import com.example.domain.repository.FilterDataRepository
import com.example.domain.response.RecordData
import com.example.domain.usecase.authentication.ProfileUseCase
import com.example.domain.usecase.data.FilteredUseCase
import com.example.features.presentation.home.state.HomeUiState
import com.example.features.presentation.home.utils.toFilterDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    filteredUseCase: FilteredUseCase,
    profileUseCase: ProfileUseCase,
    repository: FilterDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _tokenExpired = MutableSharedFlow<Unit>()

    private val _searchQuery = MutableStateFlow<Map<String, String>>(emptyMap())
    val search = _searchQuery.asStateFlow()

    private val _searchCategory = MutableStateFlow<Map<String, String>>(emptyMap())

    private val _dataEvent = Channel<DataEvent>(Channel.BUFFERED)
    val dataEvent = _dataEvent.receiveAsFlow()

    var userProfile by mutableStateOf<UserProfile?>(null)
        private set

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
                    _tokenExpired.emit(Unit)
                }
            }
        }
    }


    fun applyCategories(category: String?) {
        _searchCategory.value = if (category.isNullOrEmpty()) emptyMap() else mapOf(
            "idproject_category" to category
        )
    }

    fun applyProjectName(names: Map<String, String>) {
        _searchQuery.value = if (names.isEmpty()) emptyMap() else names
    }

    val currentPagingData: Flow<PagingData<RecordData>> =
        combine(
            _searchQuery.debounce(300).distinctUntilChanged(),
            _searchCategory.debounce(300).distinctUntilChanged()
        ) { query, category ->
            query to category
        }.flatMapLatest { (query, category) ->
            val merge = query + category
            filteredUseCase(filterData = merge.toFilterDataModel())
        }.catch { e ->
            when(e){
                is TokenExpiredException -> _tokenExpired.emit(Unit)
                is Exception -> _dataEvent.send(DataEvent.ShowSnackBar(e.message?: Constant.UNKNOWN_ERROR))
                else -> throw e
            }
        }.cachedIn(viewModelScope)

    fun favoriteChecked(favorite: Boolean) {
        _uiState.update { it.copy(isFavorite = favorite) }
    }
}


sealed class DataEvent {
    data object Success : DataEvent()
    data class ShowSnackBar(val message: String) : DataEvent()
}