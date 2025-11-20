package com.example.features.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.response.RegenciesResponse
import com.example.domain.usecase.location.CityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityUseCase: CityUseCase
) : ViewModel() {
    private val _cityList = MutableStateFlow<Result<RegenciesResponse>>(Result.Loading)
    val cityList: StateFlow<Result<RegenciesResponse>> = _cityList

    private val _cityEvent = MutableSharedFlow<CityEvent>()

    private val _cityState = MutableStateFlow(CityState())
    val cityState = _cityState.asStateFlow()

    private var lastProvinceId: String? = null // ✅ cache id province terakhir

    fun getCity(idProvince: String?) {
        if (idProvince.isNullOrEmpty()) return
        if (idProvince == lastProvinceId && _cityList.value is Result.Success){
            return // ✅ kalau provinsi sama & data sudah ada, skip fetch
        }
        lastProvinceId = idProvince

        viewModelScope.launch(Dispatchers.IO) {
            _cityList.value = Result.Loading
            cityUseCase("", idProvince, "")
                .catch { e ->
                    _cityEvent.emit(CityEvent.Error(e.message ?: "Terjadi kesalahan"))
                }.collectLatest {
                    _cityList.value = it
                    _cityEvent.emit(CityEvent.Success)
                }
        }
    }

    fun setProvinceToCity(idProvince:String?){
        _cityState.update { current ->
            current.copy(idProvince = idProvince)
        }

        if (!idProvince.isNullOrEmpty()){
            getCity(idProvince)
        }
    }

    fun updateCity(idCity: String,idProvince: String?, city:String){
        _cityState.update { it.copy(
            idCity = idCity,
            idProvince = idProvince,
            cityName = city,
        ) }
    }

    fun clearCity() {
        _cityState.update { CityState() }
        _cityList.value = Result.Loading
    }
}


sealed class CityEvent {
    data object Success : CityEvent()
    data class Error(val message: String) : CityEvent()
}

data class CityState(
    val idCity: String = "",
    val idProvince: String? = null,
    val cityName: String = "",
    val isLoading: Boolean = false
)