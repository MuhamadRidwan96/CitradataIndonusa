package com.example.features.presentation.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repositoryImpl.DataRepositoryImpl
import com.example.domain.preferences.UserPreferences
import com.example.domain.repository.DataRepository
import com.example.domain.response.RecordData
import com.example.domain.usecase.data.DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dataUseCase: DataUseCase,
    repository: DataRepository,


) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _tokenExpired = MutableSharedFlow<Unit>()
    val tokenExpired: SharedFlow<Unit> = _tokenExpired

    private val _dataEvent = Channel<DataEvent>(Channel.BUFFERED)
    val dataEvent = _dataEvent.receiveAsFlow()

    val dataPaging: Flow<PagingData<RecordData>> = dataUseCase()
        .cachedIn(viewModelScope)

    init {
        if (repository is DataRepositoryImpl){
            repository.onTokenExpiredCallBack = {
                viewModelScope.launch {
                    _tokenExpired.emit(Unit)
                }
            }
        }
    }

    suspend fun sendEvent(event: DataEvent) {
        _dataEvent.send(event)
    }

    fun showSnackBar(message: String) {
        viewModelScope.launch {
            _dataEvent.send(DataEvent.ShowSnackBar(message))
        }
    }

    fun favoriteChecked(favorite: Boolean) {
        _uiState.update { it.copy(isFavorite = favorite) }
    }
}

@Immutable
data class HomeUiState(
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val selectedFilter: String = "",
    val showDialog: Boolean = false
)

sealed class DataEvent {
    data object Success : DataEvent()
    data class ShowSnackBar(val message: String) : DataEvent()
}