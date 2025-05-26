package com.example.features.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.Constant
import com.example.domain.model.ProvinceModel
import com.example.domain.usecase.location.ProvinceUseCase
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
class ProvinceViewModel @Inject constructor(
    private val provinceUseCase: ProvinceUseCase
) : ViewModel() {

    private val _provinceList = MutableStateFlow<List<ProvinceModel>>(emptyList())
    val provinceList: StateFlow<List<ProvinceModel>> = _provinceList

    private val _provinceEvent = MutableSharedFlow<ProvinceEvent>()
    val provinceEvent: SharedFlow<ProvinceEvent> = _provinceEvent

    private val _provinceState = MutableStateFlow(ProvinceState())
    val provinceStateViewModel = _provinceState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun getProvinces() {
        viewModelScope.launch {
            provinceUseCase(ProvinceModel()).collectLatest { result ->
                result.onSuccess { response ->
                    val provinces = response.data.map {
                        ProvinceModel(idProvince = it.idProvince, name = it.province)
                    }
                    _provinceList.value = provinces
                    _provinceEvent.emit(ProvinceEvent.Success)
                }.onFailure { e ->
                    _provinceEvent.emit(
                        ProvinceEvent.Error(
                            e.message ?: Constant.UNKNOWN_ERROR
                        )
                    )
                }
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
    val idProvince : String = "",
    val provinceName : String = "",
    val provinceSelected:String = ""
)

