package com.example.features.presentation.favorite.utils

import com.example.domain.model.FavoriteProject
import com.example.features.presentation.favorite.state.FavoriteProjectUiState

fun FavoriteProject.toUiItem() = FavoriteProjectUiState(
    idProject = idProject,
    lastUpdate = lastUpdate,
    idRecord = idRecord,
    project = project,
    statProject = statProject,
    category = category,
    status = status,
    location = location,
    province = province
)