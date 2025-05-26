package com.example.data.remote.api

import com.example.domain.model.CityModel
import com.example.domain.model.FilterDataModel
import com.example.domain.model.LoginModel
import com.example.domain.model.ProvinceModel
import com.example.domain.model.RegisterModel
import com.example.domain.response.DataResponse
import com.example.domain.response.LoginResponse
import com.example.domain.response.ProfileResponse
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.ProvinceResponse
import com.example.domain.response.RegenciesResponse
import com.example.domain.response.RegisterResponse
import com.example.domain.response.UpdateProfileResponse
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

    override suspend fun searchData(page: Int, limit:Int,search: Map<String, String>): Response<DataResponse> {
        return apiService.searchData(page,limit,search)
    }

    override suspend fun getData(page: Int,limit:Int): Response<DataResponse> {
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

    override suspend fun getProvince(province: ProvinceModel?): Response<ProvinceResponse> {
       return apiService.getProvince(province)
    }

    override suspend fun getCity(city: CityModel): Response<RegenciesResponse> {
        return apiService.getCity(city)
    }

    override suspend fun filterData(
        page: Int,
        limit: Int,
        filteredData: FilterDataModel
    ): Response<DataResponse> {
        return apiService.filterData(page,limit,filteredData)
    }


}