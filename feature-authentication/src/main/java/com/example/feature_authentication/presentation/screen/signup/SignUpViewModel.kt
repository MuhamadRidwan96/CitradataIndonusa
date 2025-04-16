package com.example.feature_authentication.presentation.screen

import androidx.lifecycle.ViewModel
import com.example.feature_authentication.state.SignUpFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
):ViewModel() {

    private val _uiState = MutableStateFlow(SignUpFormState())
    val uiState:StateFlow<SignUpFormState> = _uiState

}