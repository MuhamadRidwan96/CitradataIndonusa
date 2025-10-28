package com.example.data.repositoryImpl

import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.toResult
import com.example.domain.repository.StatisticRepository
import com.example.domain.response.StatisticsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) :
    StatisticRepository {
    override suspend fun getStatistic(): Flow<Result<StatisticsResponse>> = flow {
        val response = apiHelper.getStatistic()
        emit(response.toResult())
    }
}