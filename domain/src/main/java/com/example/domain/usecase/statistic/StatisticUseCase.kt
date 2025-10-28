package com.example.domain.usecase.statistic

import com.example.common.Result
import com.example.domain.repository.StatisticRepository
import com.example.domain.response.StatisticsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Result<StatisticsResponse>> {
        return withContext(dispatcher) { statisticRepository.getStatistic() }
    }
}