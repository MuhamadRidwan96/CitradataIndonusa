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

interface ApiHelper{
    /**
     * Authentication
     */

    suspend fun login(requestLogin:LoginModel):Response<LoginResponse>
    suspend fun register(requestRegister: RegisterModel):Response<RegisterResponse>

    /**
     * Data Project
     */
    suspend fun searchData(page: Int,limit: Int,filters:Map<String,String>):Response<DataResponse<RecordData>>
    suspend fun getData(page: Int, limit : Int):Response<DataResponse<RecordData>>
    suspend fun filterData(page: Int, limit: Int,filteredData: FilterDataModel?): Response<DataResponse<RecordData>>

    suspend fun getDetailData(idProject:String):Response<ProjectDetailResponse>

    suspend fun getProvince(province: Province?): Response<ProvinceResponse>
    suspend fun getCity(city: CityRequest): Response<RegenciesResponse>

    /**
     *  Profile
     */
    suspend fun getUser():Response<ProfileResponse>
    suspend fun updateProfile():Response<UpdateProfileResponse>

    /**
     * Session
     **/

    suspend fun saveToken(userId:String,token: String) : Response<ResponseBody>

    /**
     *  Statistic
     **/

    suspend fun getStatistic(): Response<StatisticsResponse>

    /**
     * Notification
     **/
    suspend fun notificationList(userId: String) : NotificationResponse
    suspend fun deleteNotification(id:Int,userId: String) : Response<ResponseBody>
    suspend fun readNotification(id:Int,userId: String) : Response<ResponseBody>
    suspend fun unreadNotification(userId: String) : Response<ResponseBody>

    /**
     * Subscription
     * */

    suspend fun plan() : Response<SubscriptionListResponse>
    suspend fun subscriptionPlan(id:Int) : Response<SubscriptionPlanDetailResponse>
    suspend fun createSubscription(createSubs : CreatedSubscriptionRequest) : Response<CreateSubscriptionResponse>
    suspend fun getMySubscription() : Response<SubscriptionMeResponse>
    suspend fun cancelSubscription(id:Long) : Response<SubscriptionCancelResponse>
    suspend fun checkFeature(featureCode:String) : Response<SubscriptionFeatureResponse>
}

