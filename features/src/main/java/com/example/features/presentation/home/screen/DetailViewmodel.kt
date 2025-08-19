package com.example.features.presentation.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.domain.usecase.data.DetailDataUseCase
import com.example.features.presentation.home.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewmodel @Inject constructor(
    private val detailDataUseCase: DetailDataUseCase
) : ViewModel() {

    private val _dataState = MutableStateFlow(DetailState())
    val dataState: StateFlow<DetailState> = _dataState.asStateFlow()

    fun fetchDetailData(projectId: String) {
        viewModelScope.launch {
            _dataState.value = _dataState.value.copy(isLoading = true)
            detailDataUseCase(projectId)
                .catch { e ->
                    _dataState.value = _dataState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load project detail!"
                    )
                }.collect { result ->
                    when (result) {

                        is Result.Success -> {
                            val data = result.data.data
                            _dataState.update {
                                it.copy(
                                    isLoading = false,
                                    project = result.data,
                                    developer = data.developer,
                                    contractor = data.contractor,
                                    consultant = data.consultant,
                                    specification = data.projectSpecification,
                                    ppr = data.projectPpr,
                                    updateStatus = data.projectUpdateStatus,
                                    showCp = data.showCp,
                                    showCpEmail = data.showCpEmail,
                                    showCpPhone = data.showCpPhone,
                                    message = result.data.message
                                )
                            }
                        }

                        is Result.Error -> {
                            _dataState.value = _dataState.value.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Unknown Error!"
                            )
                        }

                        else -> Unit
                    }
                }
        }
    }

}