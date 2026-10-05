package com.example.data.network.api

import com.example.domain.model.authentication.LoginModel
import com.example.domain.model.authentication.RegisterModel
import com.example.domain.model.filter.FilterDataModel
import com.example.domain.model.location.Province
import com.example.domain.model.subscription.CreatedSubscriptionRequest
import com.example.domain.response.authentication.LoginResponse
import com.example.domain.response.authentication.RegisterResponse
import com.example.domain.response.data.DataResponse
import com.example.domain.response.data.ProjectDetailResponse
import com.example.domain.response.data.RecordData
import com.example.domain.response.location.CityRequest
import com.example.domain.response.location.ProvinceResponse
import com.example.domain.response.location.RegenciesResponse
import com.example.domain.response.notification.NotificationResponse
import com.example.domain.response.profile.ProfileResponse
import com.example.domain.response.profile.UpdateProfileResponse
import com.example.domain.response.statistic.StatisticsResponse
import com.example.domain.response.subscription.CreateSubscriptionResponse
import com.example.domain.response.subscription.SubscriptionCancelResponse
import com.example.domain.response.subscription.SubscriptionFeatureResponse
import com.example.domain.response.subscription.SubscriptionListResponse
import com.example.domain.response.subscription.SubscriptionMeResponse
import com.example.domain.response.subscription.SubscriptionPlanDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * Authentication
     **/
    @POST("/CitraDataIndonusa/apl/api/v1/getToken")
    suspend fun login(@Body login: LoginModel): Response<LoginResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/register")
    suspend fun register(@Body register: RegisterModel): Response<RegisterResponse>

    @POST("CitraDataIndonusa/apl/api/v1/save-token")
    @FormUrlEncoded
    suspend fun saveToken(
        @Field("user_id") userId: String,
        @Field("token") token: String
    ): Response<ResponseBody>

    /**
     * Data
     **/

    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun searchData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body filters: Map<String, String>
    ): Response<DataResponse<RecordData>>

    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun getData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
    ): Response<DataResponse<RecordData>>

    @POST("/CitraDataIndonusa/apl/api/v1/filter_data")
    suspend fun filterData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body filter: FilterDataModel? = null
    ): Response<DataResponse<RecordData>>

    @POST("CitraDataIndonusa/apl/api/master/statistic")
    suspend fun getStatistic(): Response<StatisticsResponse>

    @GET("/CitraDataIndonusa/apl/api/v1/project/detail/{idProject}")
    suspend fun getDetailData(
        @Path("idProject") idProject: String
    ): Response<ProjectDetailResponse>

    /**
     * User
     **/

    @GET("CitraDataIndonusa/apl/api/v1/getUser")
    suspend fun getUser(): Response<ProfileResponse>

    @POST("/CitraDaraIndonusa/apl/api/vi/update-profile")
    suspend fun updateProfile(): Response<UpdateProfileResponse>

    /**
     * Location
     **/

    @POST("/CitraDataIndonusa/apl/api/master/Province/province")
    suspend fun getProvince(
        @Body search: Province? = null
    ): Response<ProvinceResponse>

    @POST("/CitraDataIndonusa/apl/api/master/city")
    suspend fun getCity(
        @Body search: CityRequest
    ): Response<RegenciesResponse>


   /**
    * Notification
    **/

    @GET("CitraDataIndonusa/apl/api/master/Notification/list")
    suspend fun listNotification(
        @Query("user_id") userId: String
    ): NotificationResponse

    @GET("CitraDataIndonusa/apl/api/master/Notification/unread")
    suspend fun unreadNotification(
        @Query("user_id") userId: String
    ): Response<ResponseBody>


    @FormUrlEncoded
    @POST("CitraDataIndonusa/apl/api/master/Notification/delete")
    suspend fun deleteNotification(
        @Field("id") id: Int,
        @Field("user_id") userId: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("CitraDataIndonusa/apl/api/master/Notification/read")
    suspend fun readNotification(
        @Field("id") id: Int,
        @Field("user_id") userId: String
    ): Response<ResponseBody>


    /**
     * Subscription
     **/

    @GET("CitraDataIndonusa/api/master/subscription/plans")
    suspend fun getSubscriptionPlans(): Response<SubscriptionListResponse>

    @GET("CitraDataIndonusa/api/master/subscription/plans/{id}")
    suspend fun getSubscriptionPlan(
        @Path("id") id :Int
    ): Response<SubscriptionPlanDetailResponse>

    @POST("CitraDataIndonusa/api/master/subscription")
    suspend fun createSubscription(
        @Body request : CreatedSubscriptionRequest
    ) : Response<CreateSubscriptionResponse>

    @GET("CitraDataIndonusa/api/master/subscription/me/")
    suspend fun getMySubscription(): Response<SubscriptionMeResponse>

    @POST("CitraDataIndonusa/api/master/subscription/{id}/cancel")
    suspend fun cancelSubscription(
        @Path("id") subscriptionId: Long
    ): Response<SubscriptionCancelResponse>

    @GET("CitraDataIndonusa/api/master/subscription/feature/{feature}")
    suspend fun checkFeature(
        @Path("feature") featureCode: String
    ): Response<SubscriptionFeatureResponse>

}