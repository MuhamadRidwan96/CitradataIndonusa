package com.example.features.presentation.search.state.search

import com.example.core_ui.architecture.base.BaseUiState
import com.example.domain.model.FavoriteProject
import com.example.features.presentation.search.state.ProjectFilterState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SearchUiState(
    val appliedFilter: ProjectFilterState = ProjectFilterState(),

    val draftFilter: ProjectFilterState = ProjectFilterState(),

    val favorites: ImmutableList<FavoriteProject> = persistentListOf(),

    val hasSearched: Boolean = false,

    val isInitialized: Boolean = false,

    val queryChange : String = "",

    val idProject : Int? = null

) : BaseUiState
