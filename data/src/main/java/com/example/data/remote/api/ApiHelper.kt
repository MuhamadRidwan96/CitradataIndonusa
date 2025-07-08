package com.example.data.remote.api

import com.example.domain.model.CityModel
import com.example.domain.model.FilterDataModel
import com.example.domain.response.DataResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.RegisterResponse
import com.example.domain.model.LoginModel
import com.example.domain.model.ProvinceModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.ProfileResponse
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import com.example.domain.response.UpdateProfileResponse
import retrofit2.Response

interface ApiHelper{
    suspend fun login(requestLogin:LoginModel):Response<LoginResponse>
    suspend fun register(requestRegister: RegisterModel):Response<RegisterResponse>
    suspend fun searchData(page: Int,limit: Int,search:Map<String,String>):Response<DataResponse>
    suspend fun getData(page: Int, limit : Int):Response<DataResponse>
    suspend fun getDetailData(idProject:String):Response<ProjectDetailResponse>
    suspend fun getUser():Response<ProfileResponse>
    suspend fun updateProfile():Response<UpdateProfileResponse>
    suspend fun getProvince(province: ProvinceModel?): Response<ProvinceResponse>
    suspend fun getCity(city: CityModel): Response<RegenciesResponse>
    suspend fun filterData(page: Int, limit: Int,filteredData: FilterDataModel?): Response<DataResponse>

}

