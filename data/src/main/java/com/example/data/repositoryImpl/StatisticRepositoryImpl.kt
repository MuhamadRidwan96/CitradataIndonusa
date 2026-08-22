package com.example.data.repositoryImpl

import com.example.data.network.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.StatisticRepository
import com.example.domain.response.StatisticsResponse
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) :
    StatisticRepository {
    override suspend fun getStatistic(): Result<StatisticsResponse>{
        return apiHelper.getStatistic().toResult()
    }
}