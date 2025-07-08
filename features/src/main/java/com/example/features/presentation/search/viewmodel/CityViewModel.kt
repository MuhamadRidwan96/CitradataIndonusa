package com.example.features.presentation.search.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.asSharedFlow
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
    val cityEvent = _cityEvent.asSharedFlow()

    private val _cityState = MutableStateFlow(CityState())
    val cityState = _cityState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun getCity(idProvince: String?) {
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