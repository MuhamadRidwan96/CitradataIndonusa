package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.data.utils.Constant
import com.example.domain.model.Province
import com.example.domain.usecase.location.CityUseCase
import com.example.domain.usecase.location.ProvinceUseCase
import com.example.features.presentation.search.state.location.LocationUiAction
import com.example.features.presentation.search.state.location.LocationUiEvent
import com.example.features.presentation.search.state.location.LocationUiState
import com.example.features.presentation.search.utils.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel yang mengelola data lokasi (Provinsi & Kota).
 * Menggunakan single LocationState sebagai sumber kebenaran (SSOT),
 * sehingga UI dapat mengamati state secara terpadu.
 *
 */

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val provinceUseCase: ProvinceUseCase,
    private val cityUseCase: CityUseCase

) : BaseViewModel<
        LocationUiState,
        LocationUiEvent
        >(initialState = LocationUiState()), ActionHandler<LocationUiAction> {

    override fun action(action: LocationUiAction) {
        when (action) {
            LocationUiAction.Refresh -> {
                refresh()
            }

            is LocationUiAction.LoadProvince -> {
                loadProvinces()
            }

            is LocationUiAction.LoadCity -> {
                loadCity(action.idProvince)
            }
        }
    }

    fun loadProvinces() {
        if (currentState.provinceLoaded) {
            return
        }

        reduce {
            copy(
                isProvinceLoading = true
            )
        }

        viewModelScope.launch {

            provinceUseCase(Province())
                .fold(
                    onSuccess = { provinces ->

                        reduce {
                            copy(
                                provinces = provinces
                                    .map { it.toUiState() }
                                    .toImmutableList(),

                                provinceLoaded = true,
                                isProvinceLoading = false
                            )
                        }
                    },

                    onFailure = { exception ->

                        reduce {
                            copy(
                                isProvinceLoading = false
                            )
                        }

                        sendEvent(
                            LocationUiEvent.ShowSnackBar(
                                exception.message
                                    ?: Constant.UNKNOWN_ERROR
                            )
                        )
                    }
                )
        }
    }

    // -----------------------------------------------------------------------
    // CITY
    // -----------------------------------------------------------------------

    /**
     * Load daftar kota berdasarkan province ID.
     * Menggunakan caching agar tidak fetch ulang jika province ID sama.
     */
    fun loadCity(idProvince: String?) {

        if (idProvince.isNullOrBlank()) return

        val state = currentState

        // Sudah ada cache
        if (state.citiesByProvince.containsKey(idProvince)) {
            return
        }

        // Province ini sedang loading
        if (state.loadingCityProvinceId == idProvince) {
            return
        }

        reduce {
            copy(
                loadingCityProvinceId = idProvince
            )
        }

        viewModelScope.launch {

            cityUseCase(
                idCity = "",
                idProvince = idProvince,
                cityName = ""
            ).fold(

                onSuccess = { cities ->

                    val uiCities = cities
                        .map { it.toUiState() }
                        .toImmutableList()

                    val updatedCities =
                        currentState.citiesByProvince
                            .toPersistentMap()
                            .put(
                                idProvince,
                                uiCities
                            )

                    reduce {
                        copy(
                            citiesByProvince = updatedCities,
                            loadingCityProvinceId = null
                        )
                    }
                },

                onFailure = { exception ->

                    reduce {
                        copy(
                            loadingCityProvinceId = null
                        )
                    }

                    sendEvent(
                        LocationUiEvent.ShowSnackBar(
                            exception.message
                                ?: Constant.UNKNOWN_ERROR
                        )
                    )
                }
            )
        }
    }


    // ============================================================
    // REFRESH
    // ============================================================

    private fun refresh() {
        reduce {
            copy(
                provinces = persistentListOf(),
                citiesByProvince = persistentMapOf(),
                loadingCityProvinceId = null,
                provinceLoaded = false
            )
        }
        loadProvinces()
    }
}





