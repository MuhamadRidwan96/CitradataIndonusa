package com.example.domain.repository

import com.example.domain.response.StatisticsResponse

interface StatisticRepository {
    suspend fun getStatistic(): Result<StatisticsResponse>
}