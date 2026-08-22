package com.example.domain.usecase.statistic

import com.example.domain.di.IoDispatcher
import com.example.domain.repository.StatisticRepository
import com.example.domain.response.StatisticsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Result<StatisticsResponse> {
        return withContext(dispatcher) { statisticRepository.getStatistic() }
    }
}