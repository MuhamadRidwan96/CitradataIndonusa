package com.example.features.presentation.search.state

sealed interface SearchBottomSheetAction {

    data class SetWithPpr(val value: Boolean) : SearchBottomSheetAction

    data class SetStartDate(val date: String) : SearchBottomSheetAction
    data class SetEndDate(val date: String) : SearchBottomSheetAction
    object ClearStartDate : SearchBottomSheetAction
    object ClearEndDate : SearchBottomSheetAction

    data class SelectStatus(val id: Int?, val name: String) : SearchBottomSheetAction
    data class SelectBuildingCategory(val id: Int?, val name: String) : SearchBottomSheetAction
    data class SelectProjectCategory(val id: Int?, val name: String) : SearchBottomSheetAction

    data class QueryChange(val query: String) : SearchBottomSheetAction
    data class SelectProvince(val id: String?, val name: String) : SearchBottomSheetAction
    data class SelectCity(val id: String?, val provinceId: String?, val name: String) : SearchBottomSheetAction

    object Apply : SearchBottomSheetAction
    object Dismiss : SearchBottomSheetAction

}