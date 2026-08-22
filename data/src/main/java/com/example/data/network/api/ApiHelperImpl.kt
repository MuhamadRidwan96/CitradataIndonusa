package com.example.data.network.api

import com.example.domain.model.FilterDataModel
import com.example.domain.model.LoginModel
import com.example.domain.model.Province
import com.example.domain.model.RegisterModel
import com.example.domain.response.CityRequest
import com.example.domain.response.DataResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.NotificationResponse
import com.example.domain.response.ProfileResponse
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RecordData
import com.example.domain.response.RegenciesResponse
import com.example.domain.response.RegisterResponse
import com.example.domain.response.StatisticsResponse
import com.example.domain.response.UpdateProfileResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {
    override suspend fun login(requestLogin: LoginModel): Response<LoginResponse> {
        return apiService.login(requestLogin)
    }

    override suspend fun register(requestRegister: RegisterModel): Response<RegisterResponse> {
       return apiService.register(requestRegister)
    }

    override suspend fun searchData(page: Int, limit:Int,filters: Map<String, String>): Response<DataResponse<RecordData>> {
        return apiService.searchData(page,limit,filters)
    }

    override suspend fun getData(page: Int,limit:Int): Response<DataResponse<RecordData>> {
       return apiService.getData(page,limit)
    }

    override suspend fun getDetailData(idProject: String): Response<ProjectDetailResponse> {
        return apiService.getDetailData(idProject)
    }

    override suspend fun getUser(): Response<ProfileResponse> {
        return apiService.getUser()
    }

    override suspend fun updateProfile(): Response<UpdateProfileResponse> {
       return apiService.updateProfile()
    }

    override suspend fun getProvince(province: Province?): Response<ProvinceResponse> {
       return apiService.getProvince(province)
    }

    override suspend fun getCity(city: CityRequest): Response<RegenciesResponse> {
        return apiService.getCity(city)
    }

    override suspend fun filterData(
        page: Int,
        limit: Int,
        filteredData: FilterDataModel?
    ): Response<DataResponse<RecordData>> {
        return apiService.filterData(page,limit,filteredData)
    }

    override suspend fun saveToken(
        userId: String,
        token: String
    ): Response<ResponseBody> {
        return apiService.saveToken(userId,token)
    }

    override suspend fun getStatistic(): Response<StatisticsResponse> {
        return apiService.getStatistic()
    }

    override suspend fun notificationList(userId: String): NotificationResponse {
        return apiService.listNotification(userId)
    }


    override suspend fun deleteNotification(
        id: Int,
        userId: String
    ): Response<ResponseBody> {
       return apiService.deleteNotification(id,userId)
    }

    override suspend fun readNotification(
        id: Int,
        userId: String
    ): Response<ResponseBody> {
       return apiService.readNotification(id,userId)
    }

    override suspend fun unreadNotification(userId: String): Response<ResponseBody> {
        return apiService.unreadNotification(userId)
    }
}