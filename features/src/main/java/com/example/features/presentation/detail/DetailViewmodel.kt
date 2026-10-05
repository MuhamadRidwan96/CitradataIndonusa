package com.example.features.presentation.detail

import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.data.utils.Constant
import com.example.domain.usecase.data.DetailDataUseCase
import com.example.features.presentation.detail.state.DetailUiAction
import com.example.features.presentation.detail.state.DetailUiEvent
import com.example.features.presentation.detail.state.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewmodel @Inject constructor(
    private val detailDataUseCase: DetailDataUseCase
) : BaseViewModel<
        DetailUiState,
        DetailUiEvent
        >(initialState = DetailUiState()), ActionHandler<DetailUiAction> {

    override fun action(action: DetailUiAction) {
        when (action) {
            is DetailUiAction.LoadDetail -> {
                fetchDetailData(
                    action.projectId
                )
            }

            is DetailUiAction.Retry -> {
                fetchDetailData(
                    action.projectId
                )
            }
        }
    }


    fun fetchDetailData(projectId: String) {
        viewModelScope.launch {
            reduce {
                copy(
                    isLoading = true
                )
            }
            detailDataUseCase(projectId)
                .fold(
                    onSuccess = { response ->
                        val data = response.data
                        reduce {
                            copy(
                                isLoading = false,
                                error = null,
                                project = response,

                                developer = data.developer.toImmutableList(),
                                contractor = data.contractor.toImmutableList(),
                                consultant = data.consultant.toImmutableList(),
                                specification = data.projectSpecification.toImmutableList(),
                                ppr = data.projectPpr.toImmutableList(),
                                updateStatus = data.projectUpdateStatus.toImmutableList(),

                                showCp = data.showCp,
                                showCpEmail = data.showCpEmail,
                                showCpPhone = data.showCpPhone
                            )
                        }


                    },
                    onFailure = { e ->
                        val message = e.message ?: Constant.UNKNOWN_ERROR

                        reduce {
                            copy(
                                isLoading = false,
                                error = message
                            )
                        }
                        sendEvent(
                            DetailUiEvent.ShowSnackBar(
                                message
                            )
                        )
                    }
                )
        }
    }
}