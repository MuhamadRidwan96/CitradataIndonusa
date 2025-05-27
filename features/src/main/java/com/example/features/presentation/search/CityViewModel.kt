package com.example.features.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constant
import com.example.domain.model.CityModel
import com.example.domain.usecase.location.CityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityUseCase: CityUseCase
) : ViewModel() {
    private val _cityList = MutableStateFlow<List<CityModel>>(emptyList())
    val cityList: StateFlow<List<CityModel>> = _cityList

    private val _cityEvent = MutableSharedFlow<CityEvent>()
    val cityEvent: SharedFlow<CityEvent> = _cityEvent

    private val _cityState = MutableStateFlow(CityState())
    val cityState = _cityState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
    fun getCity(idProvince: String?) {

        viewModelScope.launch {
            cityUseCase("",idProvince, "").collectLatest { result ->
                result.onSuccess { response ->
                    val cities = response.data.map {
                        CityModel(
                            idCity = it.idCity,
                            idProvince = it.idProvince ?: "",
                            cityName = it.cityName,
                        )
                    }
                    _cityList.value = cities
                    _cityEvent.emit(CityEvent.Success)
                }.onFailure { e ->
                    _cityEvent.emit(
                        CityEvent.Error(
                            e.message ?: Constant.UNKNOWN_ERROR
                        )
                    )
                }
            }
        }
    }

    fun updateCity(
        idCity: String,
        idProvince: String?,
        name: String
    ) {
        _cityState.update { current ->
            current.copy(
                idCity = idCity,
                idProvince = idProvince,
                cityName = name
            )
        }
        _cityList.value = emptyList()
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