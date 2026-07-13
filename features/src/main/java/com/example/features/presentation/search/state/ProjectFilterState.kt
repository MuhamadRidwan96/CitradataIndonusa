package com.example.features.presentation.search.state

import androidx.compose.runtime.Immutable

@Immutable
data class ProjectFilterState(
    val startDate: String = "",
    val endDate: String = "",

    val idProject: String = "",
    val projectName: String = "",

    val idProjectCategory: Int? = null,
    val projectCategoryName : String = "",

    val idBuildingCategory: Int? = null,
    val buildingCategoryName: String = "",

    val address: String = "",

    val idProvince: String? = "",
    val provinceName : String = "",

    val idCity: String? = "",
    val cityName : String = "",

    val idDeveloper: Int? = null,
    val idConsultant: String = "",
    val idContractor: String = "",
    val idSectorCategory: String = "",

    val idProjectStatusCategory: Int ?=null,
    val statusCategory: String = "",

    val idConsultantCategory:String = "",
    val idContractorCategory : String = "",

    val withPpr: Boolean = false,
    val ppr : String = "",
    val queryProvince: String = "",
    val queryCity: String = "",
    val isLoading: Boolean = false,
    val onClearStartDate: String = "",
    val onClearEndDate: String = "",
    val isFavorite: Boolean = false,
)

fun ProjectFilterState.hasFilter() : Boolean {
    return startDate.isNotEmpty() ||
            endDate.isNotEmpty() ||
            idProject.isNotEmpty() ||
            projectName.isNotEmpty() ||
            idProjectCategory != null ||
            idBuildingCategory != null ||
            address.isNotEmpty() ||
            !idProvince.isNullOrEmpty() ||
            !idCity.isNullOrEmpty() ||
            idDeveloper != null ||
            idConsultant.isNotEmpty() ||
            idContractor.isNotEmpty() ||
            idSectorCategory.isNotEmpty() ||
            idProjectStatusCategory != null ||
            idConsultantCategory.isNotEmpty() ||
            idContractorCategory.isNotEmpty() ||
            withPpr || // boolean filter
            ppr.isNotEmpty() ||
            //query.isNotEmpty() ||
            queryProvince.isNotEmpty() ||
            queryCity.isNotEmpty()
}