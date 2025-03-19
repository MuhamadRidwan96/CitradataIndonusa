package com.example.data.remote

import com.example.data.response.DataResponse
import com.example.data.response.LoginResponse
import com.example.data.response.ProjectDetailResponse
import com.example.data.response.ProvinceResponse
import com.example.data.response.RegenciesResponse
import com.example.data.response.RegisterResponse
import com.example.domain.model.CityModel
import com.example.domain.model.FilterDataModel
import com.example.domain.model.LoginModel
import com.example.domain.model.ProvinceModel
import com.example.domain.model.RegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
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

    @POST("/CitraDataIndonusa/apl/api/v1/home")
    suspend fun getData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
    ): Response<DataResponse>

    @GET("/CitraDataIndonusa/apl/api/v1/project/detail/{idProject}")
    suspend fun getDetailProject(
        @Path("idProject") idProject: String
    ): Response<ProjectDetailResponse>

    @POST("/CitraDataIndonusa/apl/api/v1/filter_data")
    suspend fun filterData(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body request: FilterDataModel
    ): DataResponse {
        return this.filterData(page, limit, request)
    }

    @POST("/CitraDataIndonusa/apl/api/master/province")
    suspend fun getProvince(
        @Header("Auth-Token") authToken: String,
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body search: ProvinceModel
    ): ProvinceResponse

    @POST("/CitraDataIndonusa/apl/api/master/city")
    suspend fun getCity(
        @Header("Page") page: Int,
        @Header("Limit") limit: Int,
        @Body search: CityModel
    ): RegenciesResponse

}