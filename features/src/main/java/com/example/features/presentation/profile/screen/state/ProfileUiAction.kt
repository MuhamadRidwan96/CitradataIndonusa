package com.example.features.presentation.profile.screen.state

import com.example.core_ui.architecture.base.BaseUiAction

interface ProfileUiAction : BaseUiAction {


    data object Refresh : ProfileUiAction

    data object Logout : ProfileUiAction

}