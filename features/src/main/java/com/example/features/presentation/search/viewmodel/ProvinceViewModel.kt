package com.example.features.presentation.search.viewmodel

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

    private val _provinceState = MutableStateFlow(ProvinceState())
    val provinceStateViewModel = _provinceState.asStateFlow()

    private var isLoaded = false // flag, supaya tidak fetch berulang

    fun getProvinces() {
        if (isLoaded) return // ✅ kalau sudah pernah load, skip
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
                    isLoaded = true
                }
        }
    }

    fun updateProvinces(idProvince : String,province: String){
        _provinceState.update { it.copy(
            idProvince = idProvince,
            provinceName = province,
        ) }
    }

    fun clearProvince(){
        _provinceState.update { ProvinceState() }
        _provinceList.value = Result.Loading
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

