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
     */
    suspend fun saveToken(userId:String,token: String) : Response<ResponseBody>
    /**
     *  Statistic
     */

    suspend fun getStatistic(): Response<StatisticsResponse>

    /**
     * Notification
     */
    suspend fun notificationList(userId: String) : NotificationResponse
    suspend fun deleteNotification(id:Int,userId: String) : Response<ResponseBody>
    suspend fun readNotification(id:Int,userId: String) : Response<ResponseBody>
    suspend fun unreadNotification(userId: String) : Response<ResponseBody>
}

