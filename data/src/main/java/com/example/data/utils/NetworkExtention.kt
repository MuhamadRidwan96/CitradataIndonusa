package com.example.data.utils

import retrofit2.Response
import com.example.common.Result

fun <T> Response<T>.toResult(): Result<T> {

    return if (isSuccessful) {
        this.body()?.let { Result.Success(it) } ?: Result.Error(Exception(Constant.ERROR_NULL))

    } else {
        val errorBody = this.errorBody()?.string()
        val errorMessage = try {
            errorBody?.let { ErrorHandle.parseErrorBody(it) ?: Constant.UNKNOWN_ERROR }
        } catch (_: Exception) {
            Constant.FAILED_PARSE
        }
        Result.Error(Exception(errorMessage))
    }
}