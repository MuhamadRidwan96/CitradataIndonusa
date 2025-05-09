package com.example.data.remote.api

import com.example.domain.response.DataResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import com.example.domain.response.RegisterResponse
import com.example.domain.model.CityModel
import com.example.domain.model.FilterDataModel
import com.example.domain.model.LoginModel
import com.example.domain.model.ProvinceModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.ProfileResponse
import com.example.domain.response.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @POST("/CitraDataIndonusa/apl/api/v1/getToken")
    suspend fun login(@Body login: LoginModel): Response<LoginResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/register")
    suspend fun register(@Body register:RegisterModel):Response<RegisterResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun searchData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body search: Map<String, String>
    ): Response<DataResponse>

    @GET("CitraDataIndonusa/apl/api/v1/getUser")
    suspend fun getUser():Response<ProfileResponse>

    @POST("/CitraDaraIndonusa/apl/api/vi/update-profile")
    suspend fun updateProfile(): Response<UpdateProfileResponse>
    
    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun getData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
    ): Response<DataResponse>

    @GET("/CitraDataIndonusa/apl/api/v1/project/detail/{idProject}")
    suspend fun getDetailData(
        @Path("idProject") idProject: String
    ): Response<ProjectDetailResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/filter_data")
    suspend fun filterData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body filter: FilterDataModel
    ): Response<DataResponse>

    @POST("/CitraDataIndonusa/apl/api/master/province")
    suspend fun getProvince(
        @Header("Auth-Token") authToken: String,
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body search: ProvinceModel
    ): Response<ProvinceResponse>

    @POST("/CitraDataIndonusa/apl/api/master/city")
    suspend fun getCity(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body search: CityModel
    ): Response<RegenciesResponse>

}