package com.example.features.presentation.search.state

data class ProjectFilterState(
    val startDate: String = "",
    val endDate: String = "",
    val idProject: String = "",
    val projectName: String = "",
    val idProjectCategory: String = "",
    val idBuildingCategory: String = "",
    val address: String = "",
    val idProvince: String = "",
    val idCity: String = "",
    val idDeveloper: Int? = null,
    val idConsultant: String = "",
    val idContractor: String = "",
    val idSectorCategory: String = "",
    val idProjectStatusCategory: String = "",
    val withPpr: String = "",

)