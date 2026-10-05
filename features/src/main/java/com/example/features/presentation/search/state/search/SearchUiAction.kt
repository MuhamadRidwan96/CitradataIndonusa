package com.example.features.presentation.search.state.search

import com.example.core_ui.architecture.base.BaseUiAction
import com.example.data.local.entity.FavoriteProjectEntity

interface SearchUiAction : BaseUiAction {

    // =========================
    // Search
    // =========================

    data class QueryChanged(
        val query: String
    ) : SearchUiAction

    data object ApplyFilter : SearchUiAction
    data object Dismiss : SearchUiAction


    // =========================
    // Filter
    // =========================

    data class SetWithPpr(
        val value: Boolean
    ) : SearchUiAction

    data class SetStartDate(
        val startDate: String
    ) : SearchUiAction

    data class SetEndDate(
        val endDate: String
    ) : SearchUiAction

    data class SetStatus(
        val id: Int ?,
        val name: String
    ) : SearchUiAction

    data class SetBuildingCategory(
        val id: Int ?,
        val name: String
    ) : SearchUiAction

    data class SetProjectCategory(
        val id: Int?,
        val name: String
    ) : SearchUiAction

    data class AddressChanged(
        val address: String
    ) : SearchUiAction

    data class SetProvince(
        val id: String?,
        val name: String
    ) : SearchUiAction

    data class SetCity(
        val id: String?,
        val provinceId: String?,
        val name: String
    ) : SearchUiAction


    // =========================
    // Clear Filter
    // =========================

    data object ClearDateRange : SearchUiAction

    data object ClearCategory : SearchUiAction

    data object ClearPpr : SearchUiAction

    data object ClearProvince : SearchUiAction

    data object ClearCity : SearchUiAction

    data object ClearStatus : SearchUiAction

    data object ClearBuilding : SearchUiAction

    data object ClearAddress : SearchUiAction


    // =========================
    // Favorite
    // =========================

    data class ToggleFavorite(
        val favorite: FavoriteProjectEntity
    ) : SearchUiAction


    // =========================
    // Session
    // =========================

    data object LogoutClicked : SearchUiAction

    // =========================
    // Paging Error
    // =========================

    data class PagingError(
        val throwable: Throwable
    ) : SearchUiAction
}