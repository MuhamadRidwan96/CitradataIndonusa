package com.example.data.network.api

import com.example.domain.model.CityModel
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
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @POST("/CitraDataIndonusa/apl/api/v1/getToken")
    suspend fun login(@Body login: LoginModel): Response<LoginResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/register")
    suspend fun register(@Body register: RegisterModel): Response<RegisterResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun searchData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body filters: Map<String, String>
    ): Response<DataResponse<RecordData>>

    @GET("CitraDataIndonusa/apl/api/v1/getUser")
    suspend fun getUser(): Response<ProfileResponse>

    @POST("/CitraDaraIndonusa/apl/api/vi/update-profile")
    suspend fun updateProfile(): Response<UpdateProfileResponse>

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

    @POST("/CitraDataIndonusa/apl/api/master/Province/province")
    suspend fun getProvince(
        @Body search: Province? = null
    ): Response<ProvinceResponse>

    @POST("/CitraDataIndonusa/apl/api/master/city")
    suspend fun getCity(
        @Body search: CityRequest
    ): Response<RegenciesResponse>

    @POST("CitraDataIndonusa/apl/api/v1/save-token")
    @FormUrlEncoded
    suspend fun saveToken(
        @Field("user_id") userId: String,
        @Field("token") token: String
    ): Response<ResponseBody>

    @POST("CitraDataIndonusa/apl/api/master/statistic")
    suspend fun getStatistic(): Response<StatisticsResponse>

    @GET("/CitraDataIndonusa/apl/api/v1/project/detail/{idProject}")
    suspend fun getDetailData(
        @Path("idProject") idProject: String
    ): Response<ProjectDetailResponse>

    @GET("CitraDataIndonusa/apl/api/master/Notification/list")
    suspend fun listNotification(
        @Query("user_id") userId: String
    ): NotificationResponse

    @GET("CitraDataIndonusa/apl/api/master/Notification/unread")
    suspend fun unreadNotification(
        @Query("user_id") userId: String
    ) : Response<ResponseBody>


    @FormUrlEncoded
    @POST("CitraDataIndonusa/apl/api/master/Notification/delete")
    suspend fun deleteNotification(
        @Field("id") id: Int,
        @Field("user_id") userId : String
    ) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("CitraDataIndonusa/apl/api/master/Notification/read")
    suspend fun readNotification(
        @Field("id") id: Int,
        @Field("user_id") userId : String
    ) : Response<ResponseBody>
}