package com.example.data.utils

import retrofit2.Response
import com.example.common.Result
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.ResponseBody

class TokenExpiredException(message: String) : Exception(message)

fun <T> Response<T>.toResult(): Result<T> {

    return if (isSuccessful) {
        this.body()?.let { Result.Success(it) } ?: Result.Error(Exception(Constant.ERROR_NULL))

    } else {
        val errorBody = this.errorBody()?.string()
        val errorMessage = try {
            errorBody?.let {
                val parseMessage = ErrorHandle.parseErrorBody(it) ?: Constant.UNKNOWN_ERROR
                if (parseMessage == "Token Time Expired.") {
                    throw TokenExpiredException(parseMessage)
                }
                parseMessage
            }
        } catch (e: TokenExpiredException) {
            throw e
        } catch (_: Exception) {
            Constant.FAILED_PARSE
        }
        Result.Error(Exception(errorMessage))
    }
}

inline fun <reified T> Response<ResponseBody>.toTypedResult(): Result<T> {
    return try {
        val rawJson = this.body()?.string() ?: return Result.Error(Exception(Constant.ERROR_NULL))

        val jsonObject = JsonParser.parseString(rawJson).asJsonObject
        val success = jsonObject["success"]?.asBoolean == true
        val message = jsonObject["message"]?.asString ?: "Unknown"

        if (!success && message == "Token Time Expired.") {
            throw TokenExpiredException(message)
        }

        if (success) {
            val dataParsed = Gson().fromJson<T>(rawJson, T::class.java)
            Result.Success(dataParsed)
        } else {
            Result.Error(Exception(message))
        }
    } catch (e: TokenExpiredException) {
        throw e
    } catch (_: Exception) {
        Result.Error(Exception("Failed to parse response"))
    }
}

