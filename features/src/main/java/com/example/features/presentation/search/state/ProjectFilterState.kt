package com.example.features.presentation.search.state

data class ProjectFilterState(
    val startDate: String = "",
    val endDate: String = "",
    val idProject: String = "",
    val projectName: String = "",
    val idProjectCategory: Int? = null,
    val idBuildingCategory: Int? = null,
    val address: String = "",
    val idProvince: String? = "",
    val idCity: String? = "",
    val idDeveloper: Int? = null,
    val idConsultant: String = "",
    val idContractor: String = "",
    val idSectorCategory: String = "",
    val idProjectStatusCategory: String = "",
    val withPpr: String = "",
    val query: String = "",
    val queryProvince: String = "",
    val queryCity: String = "",
    val isLoading: Boolean = false,
    val onClearStartDate: String = "",
    val onClearEndDate: String = ""
)