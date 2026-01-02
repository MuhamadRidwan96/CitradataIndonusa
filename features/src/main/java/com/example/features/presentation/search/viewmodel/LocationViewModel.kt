package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.di.IoDispatcher
import com.example.domain.model.ProvinceModel
import com.example.domain.usecase.location.CityUseCase
import com.example.domain.usecase.location.ProvinceUseCase
import com.example.features.presentation.search.state.LocationEvent
import com.example.features.presentation.search.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel yang mengelola data lokasi (Provinsi & Kota).
 * Menggunakan single LocationState sebagai sumber kebenaran (SSOT),
 * sehingga UI dapat mengamati state secara terpadu.
 *
 * ViewModel ini mendukung:
 * - Caching province & city agar tidak fetch ulang
 * - SharedFlow event untuk one-time event (snackbar, toast)
 * - StateFlow untuk UI state yang stabil
 */

@HiltViewModel
class LocationViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val provinceUseCase: ProvinceUseCase,
    private val cityUseCase: CityUseCase

) : ViewModel() {

    /**
     * State utama untuk UI.
     * Berisi data province, city, selectedProvince, selectedCity, dll.
     */

    private val _locationState = MutableStateFlow(LocationState())
    val locationState: StateFlow<LocationState> = _locationState

    /**
     * Event sekali-kirim (snackbar / toast / navigation).
     */
    private val _locationEvent = MutableSharedFlow<LocationEvent>()
    val provinceEvent: SharedFlow<LocationEvent> = _locationEvent

    /**
     * Flag caching province agar tidak fetch berulang.
     */
    //private var provinceLoaded = false
    /**
     * Cache province terakhir yang digunakan untuk load city.
     */

    // -----------------------------------------------------------------------
    // PROVINCE
    // -----------------------------------------------------------------------

    /**
     * Load daftar provinsi sekali saja.
     */
    fun getProvinces() {
        val state = _locationState.value

        if (state.provinceLoaded) return // ✅ kalau sudah pernah load, skip
        viewModelScope.launch(dispatcher) {

            provinceUseCase(
                ProvinceModel(
                    idProvince = _locationState.value.selectedProvinceId,
                    name = _locationState.value.selectedProvinceName
                )
            )
                .catch { e ->
                    _locationEvent.emit(LocationEvent.Error(e.message ?: "Terjadi kesalahan"))
                }
                .collect { result ->
                    // Update UI state
                    _locationState.update { it.copy(province = result, provinceLoaded = true) }
                    // Emit event
                    _locationEvent.emit(LocationEvent.ProvinceLoaded)
                    // Tandai sudah pernah di loaded
                }
        }
    }
    /**
     * Mengubah province yang dipilih user.
     */

    fun selectedProvinces(idProvince: String, province: String) {
        _locationState.update {
            it.copy(
                selectedProvinceId = idProvince,
                selectedProvinceName = province,
            )
        }
    }
    /**
     * Menghapus data province dan reset state.
     */
    fun clearProvince() {
        _locationState.update { LocationState() }
    }

    // -----------------------------------------------------------------------
    // CITY
    // -----------------------------------------------------------------------

    /**
     * Load daftar kota berdasarkan province ID.
     * Menggunakan caching agar tidak fetch ulang jika province ID sama.
     */
    fun getCity(idProvince: String?) {
        //Cek idProvince
        if (idProvince.isNullOrEmpty()) return

        val state = _locationState.value
        // Skip jika province sama dan kota sudah sukses dimuat
        if (idProvince == state.cachedProvince && state.cities is Result.Success) return // ✅ kalau provinsi sama & data sudah ada, skip fetch

        viewModelScope.launch(dispatcher) {

            cityUseCase("", idProvince, "")
                .onStart{
                    _locationState.update { it.copy(cities = Result.Loading) }
                }
                .catch { e ->
                    _locationEvent.emit(LocationEvent.Error(e.message ?: "Terjadi kesalahan"))
                }.collectLatest { result ->
                    _locationState.update { it.copy(cities = result, cachedProvince = idProvince) }
                    _locationEvent.emit(LocationEvent.CityLoaded)
                }
        }
    }

    /**
     * Mengubah kota yang dipilih user.
     */
    fun updateCity(idCity: String, idProvince: String?, city: String) {
        _locationState.update {
            it.copy(
                selectedCityId = idCity,
                selectedProvinceId = idProvince,
                selectedCityName = city,
            )
        }
    }
    /**
     * Menghapus data kota dan reset.
     */
    fun clearCity() {
        _locationState.update { LocationState() }
    }
}





