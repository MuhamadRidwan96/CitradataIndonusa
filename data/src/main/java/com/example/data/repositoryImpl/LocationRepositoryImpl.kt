package com.example.data.repositoryImpl

import com.example.common.Result
import com.example.data.remote.api.ApiHelper
import com.example.data.utils.Constant
import com.example.data.utils.ErrorHandle
import com.example.domain.model.CityModel
import com.example.domain.model.ProvinceModel
import com.example.domain.repository.LocationRepository
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper
) : LocationRepository {
    override fun province(provinceModel: ProvinceModel?): Flow<Result<ProvinceResponse>> = flow {
        try {
            val response = apiHelper.getProvince(provinceModel)
            if (response.isSuccessful) {
                response.body()?.let { emit(Result.Success(it)) } ?: emit(
                    Result.Error(
                        Exception(
                            Constant.ERROR_NULL
                        )
                    )
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (_: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.Error(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun city(cityModel: CityModel): Flow<Result<RegenciesResponse>> = flow {

        try {
            val cityResponse = apiHelper.getCity(cityModel)
            if (cityResponse.isSuccessful) {
                cityResponse.body()?.let { emit(Result.Success(it)) } ?: emit(
                    Result.Error(
                        Exception(
                            Constant.ERROR_NULL
                        )
                    )
                )
            } else {
                val errorBody = cityResponse.errorBody()?.string()
                val errorMessage = try {
                    errorBody?.let { ErrorHandle.parseErrorBody(it) } ?: Constant.UNKNOWN_ERROR
                } catch (_: Exception) {
                    Constant.FAILED_PARSE
                }
                emit(Result.Error(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
