package com.example.domain.repository

import com.example.common.Result
import com.example.domain.response.StatisticsResponse
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    suspend fun getStatistic(): Flow<Result<StatisticsResponse>>
}