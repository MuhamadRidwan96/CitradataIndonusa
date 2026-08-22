package com.example.features.presentation.search.utils

import com.example.domain.model.CityModel
import com.example.domain.model.Province
import com.example.features.presentation.search.state.ProjectFilterState

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


fun Province.toUiState(): ProvinceUiState{
    return ProvinceUiState(
        idProvince = idProvince,
        province = province,
    )
}

fun CityModel.toUiState() : CityUiState{
    return CityUiState(
        idCity = idCity,
        idProvince = idProvince,
        cityName = cityName
    )
}
