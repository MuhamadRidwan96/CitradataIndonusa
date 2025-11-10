package com.example.features.presentation.home.screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.data.local.mapToDonut
import com.example.domain.model.DonutData
import com.example.domain.usecase.statistic.StatisticUseCase
import com.example.features.presentation.home.state.StatisticsDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val statisticUseCase: StatisticUseCase
) : ViewModel() {

    private val _statisticState = MutableStateFlow(StatisticsDataState())
    val statisticState: StateFlow<StatisticsDataState> = _statisticState.asStateFlow()

    private val _byStatus = MutableStateFlow<List<DonutData>>(emptyList())
    val byStatus = _byStatus.asStateFlow()

    fun fetchStatistic() {
        viewModelScope.launch {
            if (_statisticState.value.isLoading || _statisticState.value.totalProjects > 0) return@launch // ✅ cegah re-request

            _statisticState.value = _statisticState.value.copy(isLoading = true)

            statisticUseCase()
                .catch { e ->
                    _statisticState.update {
                        it.copy(isLoading = false, error = e.message ?: "Failed to fetch statistic")
                    }
                }.collect { result ->
                    when (result) {

                        is Result.Success -> {
                            val data = result.data.data

                            val categoryColors = mapOf(
                                "UNDER CONSTRUCTION" to Color(0xFFE74C3C),
                                "PLANNING" to Color(0xFF27AE60),
                                "POST TENDER" to Color(0xFFF1C40F),
                                "PILLING WORK" to Color(0xFF1ABC9C),
                                "HOLD PROJECT" to Color(0xFFFB8C00)
                            )

                            val total = data.totalProjects.takeIf{it > 0 } ?:1

                            val trends = withContext(Dispatchers.Default){
                                data.byCategory.mapValues{ (category,count) ->
                                    ((count.toFloat()/total.toFloat()) * 100f).roundToInt()
                                }
                            }

                            _byStatus.value = mapToDonut(data.byStatus,categoryColors)
                            _statisticState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoaded = true,
                                    totalProjects = data.totalProjects,
                                    byCategory = data.byCategory,
                                    byStatus = data.byStatus,
                                    byProvince = data.byProvince,
                                    categoryTrends = trends,

                                )
                            }
                        }

                        is Result.Error -> {
                            _statisticState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoaded = false,
                                    error = result.exception.message ?: "Unknown Error!"
                                )
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }
}