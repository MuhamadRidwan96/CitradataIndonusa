package com.example.features.presentation.search.state

import androidx.compose.runtime.Immutable
import com.example.common.Result
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse

@Immutable
data class LocationState(
    val province: Result<ProvinceResponse> = Result.Loading,
    val cities: Result<RegenciesResponse> = Result.Loading,

    val error: String? = null,

    val provinceLoaded: Boolean = false,
    val cachedProvince: String? = null,

)