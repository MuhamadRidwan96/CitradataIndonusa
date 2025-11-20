package com.example.features.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.data.utils.Constant
import com.example.domain.usecase.profile.ApiProfileUseCase
import com.example.features.presentation.home.utils.toProfileState
import com.example.features.presentation.profile.screen.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiProfileUseCase: ApiProfileUseCase
) : ViewModel() {

    private val _userProfile = MutableStateFlow(ProfileState())
    val userProfile = _userProfile.asStateFlow()

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            apiProfileUseCase().collect { result ->
                when (result) {

                    is Result.Loading -> {
                        _userProfile.value = _userProfile.value.copy(
                            statusInfo = ProfileState.StatusInfo(isLoading = true),
                        )
                    }

                    is Result.Success -> {
                        _userProfile.value = result.data.data.toProfileState()
                        _uiEvent.send(UiEvent.Success)
                    }

                    is Result.Error -> {

                        _userProfile.value = _userProfile.value.copy(
                            statusInfo = ProfileState.StatusInfo(isLoading = false),
                        )
                        _uiEvent.send(
                            UiEvent.Error(
                                message = result.exception.message ?: Constant.UNKNOWN_ERROR
                            )
                        )
                    }
                }
            }
        }
    }
}

sealed class UiEvent() {
    data object Success : UiEvent()
    data class Error(val message: String) : UiEvent()
}
