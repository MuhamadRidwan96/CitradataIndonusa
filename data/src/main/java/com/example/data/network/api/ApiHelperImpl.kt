package com.example.data.network.api

import com.example.domain.model.filter.FilterDataModel
import com.example.domain.model.authentication.LoginModel
import com.example.domain.model.location.Province
import com.example.domain.model.authentication.RegisterModel
import com.example.domain.model.subscription.CreatedSubscriptionRequest
import com.example.domain.response.location.CityRequest
import com.example.domain.response.data.DataResponse
import com.example.domain.response.authentication.LoginResponse
import com.example.domain.response.notification.NotificationResponse
import com.example.domain.response.profile.ProfileResponse
import com.example.domain.response.data.ProjectDetailResponse
import com.example.domain.response.location.ProvinceResponse
import com.example.domain.response.data.RecordData
import com.example.domain.response.location.RegenciesResponse
import com.example.domain.response.authentication.RegisterResponse
import com.example.domain.response.statistic.StatisticsResponse
import com.example.domain.response.profile.UpdateProfileResponse
import com.example.domain.response.subscription.CreateSubscriptionResponse
import com.example.domain.response.subscription.SubscriptionCancelResponse
import com.example.domain.response.subscription.SubscriptionFeatureResponse
import com.example.domain.response.subscription.SubscriptionListResponse
import com.example.domain.response.subscription.SubscriptionMeResponse
import com.example.domain.response.subscription.SubscriptionPlanDetailResponse
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

    override suspend fun plan(): Response<SubscriptionListResponse> {
        return apiService.getSubscriptionPlans()
    }

    override suspend fun subscriptionPlan(id: Int): Response<SubscriptionPlanDetailResponse> {
        return apiService.getSubscriptionPlan(id)
    }

    override suspend fun createSubscription(createSubs: CreatedSubscriptionRequest): Response<CreateSubscriptionResponse> {
        return apiService.createSubscription(createSubs)
    }

    override suspend fun getMySubscription(): Response<SubscriptionMeResponse> {
        return apiService.getMySubscription()
    }

    override suspend fun cancelSubscription(id: Long): Response<SubscriptionCancelResponse> {
       return apiService.cancelSubscription(id)
    }

    override suspend fun checkFeature(featureCode: String): Response<SubscriptionFeatureResponse> {
        return apiService.checkFeature(featureCode)
    }
}