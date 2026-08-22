package com.example.features.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.usecase.authentication.LogoutUseCase
import com.example.domain.usecase.profile.ObserverProfileUseCase
import com.example.domain.usecase.profile.RefreshProfileUseCase
import com.example.features.presentation.profile.screen.state.ProfileUiAction
import com.example.features.presentation.profile.screen.state.ProfileUiEvent
import com.example.features.presentation.profile.screen.state.ProfileUiState
import com.example.features.presentation.profile.screen.utils.toProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiProfileUseCase: ObserverProfileUseCase,
    private val refreshProfile: RefreshProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val userPreferences: UserPreferences
) : BaseViewModel<

        ProfileUiState,
        ProfileUiEvent
        >(initialState = ProfileUiState()), ActionHandler<ProfileUiAction> {

    init {
        observeProfile()
        refresh()
    }

    override fun action(action: ProfileUiAction) {
        when (action) {
            ProfileUiAction.Refresh -> {
                refresh()
            }

            ProfileUiAction.Logout -> {
                logout()
            }
        }
    }

    private fun observeProfile() {
        viewModelScope.launch {
            apiProfileUseCase()
                .collect { profile ->
                    reduce {
                        profile?.toProfileState() ?: ProfileUiState()
                    }
                }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            val session = userPreferences.getSession().first()

            if (!session.isLogin) return@launch
            try {
                refreshProfile()
            } catch (e: Exception) {
                Timber.e(e, "Failed to refresh Profile")
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {

            try {
                logoutUseCase()

                sendEvent(ProfileUiEvent.LogoutSuccess)

            } catch (e: Exception) {

                sendEvent(
                    ProfileUiEvent.ShowSnackBar(
                        e.message ?: "Logout gagal"
                    )
                )
            }
        }
    }
}
