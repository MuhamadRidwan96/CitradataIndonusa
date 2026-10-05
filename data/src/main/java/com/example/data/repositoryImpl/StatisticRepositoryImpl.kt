package com.example.data.repositoryImpl

import com.example.data.network.ApiCallHandler
import com.example.data.network.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.StatisticRepository
import com.example.domain.response.statistic.StatisticsResponse
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiCallHandler: ApiCallHandler
) :
    StatisticRepository {
    override suspend fun getStatistic(): Result<StatisticsResponse> {
        return apiCallHandler.execute { apiHelper.getStatistic().toResult() }
    }
}