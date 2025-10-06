package com.example.domain.repository

import com.example.common.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface SaveTokenRepository {
    fun saveToken(userId:String,token:String) : Flow<Result<ResponseBody>>
}