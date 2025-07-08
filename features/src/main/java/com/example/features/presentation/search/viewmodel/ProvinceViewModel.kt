package com.example.features.presentation.search.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.model.ProvinceModel
import com.example.domain.response.ProvinceResponse
import com.example.domain.usecase.location.ProvinceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvinceViewModel @Inject constructor(
    private val provinceUseCase: ProvinceUseCase
) : ViewModel() {

    private val _provinceList = MutableStateFlow<Result<ProvinceResponse>>(Result.Loading)
    val provinceList: StateFlow<Result<ProvinceResponse>> = _provinceList

    private val _provinceEvent = MutableSharedFlow<ProvinceEvent>()
    val provinceEvent = _provinceEvent.asSharedFlow()

    private val _provinceState = MutableStateFlow(ProvinceState())
    val provinceStateViewModel = _provinceState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun getProvinces() {
        viewModelScope.launch(Dispatchers.IO) {

            provinceUseCase(
                ProvinceModel(
                    provinceStateViewModel.value.idProvince,
                    provinceStateViewModel.value.provinceName
                )
            )
                .catch { e ->
                    _provinceEvent.emit(ProvinceEvent.Error(e.message ?: "Terjadi kesalahan"))
                }
                .collect {
                    _provinceList.value = it
                    _provinceEvent.emit(ProvinceEvent.Success)
                }
        }
    }


    fun updateProvince(id: String, name: String) {
        _provinceState.update { current ->
            current.copy(
                idProvince = id,
                provinceName = name
            )
        }
    }
}

sealed class ProvinceEvent {
    data object Success : ProvinceEvent()
    data class Error(val message: String) : ProvinceEvent()
}

data class ProvinceState(
    val idProvince: String = "",
    val provinceName: String = "",
    val provinceSelected: String = ""
)

