package com.example.features.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.profile.ObserverProfileUseCase
import com.example.domain.usecase.profile.RefreshProfileUseCase
import com.example.features.presentation.profile.screen.state.ProfileState
import com.example.features.presentation.profile.screen.state.toProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    apiProfileUseCase: ObserverProfileUseCase,
    private val refreshProfile: RefreshProfileUseCase
) : ViewModel() {

    val userProfile = apiProfileUseCase()
        .map { profile ->
            profile?.toProfileState() ?: ProfileState()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileState()
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            refreshProfile()
        }
    }
}
