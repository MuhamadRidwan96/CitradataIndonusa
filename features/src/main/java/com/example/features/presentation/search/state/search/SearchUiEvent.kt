package com.example.features.presentation.search.state.search

import com.example.core_ui.architecture.base.BaseUiEvent

interface SearchUiEvent : BaseUiEvent {

    data class ShowSnackBar(
        val message: String
    ) : SearchUiEvent

    data object TokenExpired : SearchUiEvent

    data object Logout : SearchUiEvent

    data class NavigateToDetail(val idProject : String) : SearchUiEvent

}