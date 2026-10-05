package com.example.domain.repository

import com.example.domain.response.statistic.StatisticsResponse

interface StatisticRepository {
    suspend fun getStatistic(): Result<StatisticsResponse>
}