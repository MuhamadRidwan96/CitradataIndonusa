package com.example.features.presentation.search.state.location

import androidx.compose.runtime.Immutable
import com.example.core_ui.architecture.base.BaseUiState
import com.example.features.presentation.search.utils.CityUiState
import com.example.features.presentation.search.utils.ProvinceUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Immutable
data class LocationUiState(
    val provinces : ImmutableList<ProvinceUiState> = persistentListOf(),

    val citiesByProvince: ImmutableMap<
            String,
            ImmutableList<CityUiState>
            > = persistentMapOf(),

    val isProvinceLoading: Boolean = false,

    val loadingCityProvinceId: String? = null,

    val provinceLoaded: Boolean = false

) : BaseUiState
