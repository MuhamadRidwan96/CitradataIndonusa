package com.example.data.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.example.common.Result
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.random.Random

class TokenExpiredException(message: String) : Exception(message)
class DataNotFoundException(message: String) : Exception(message)

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
           return Result.Error(TokenExpiredException(message))
        }
        if (!success && message == "Data Not Found.") {
            return Result.Error(DataNotFoundException(message))
        }

        if (success) {
            val typed = object : TypeToken<T>() {}.type
            val dataParsed: T = Gson().fromJson(rawJson, typed)
            Result.Success(dataParsed)
        } else {
            Result.Error(Exception(message))
        }
    } catch (e: TokenExpiredException) {
        return Result.Error(e)
    } catch (e: DataNotFoundException) {
        return Result.Error(e)
    } catch (_: Exception) {
        Result.Error(Exception("Failed to parse response"))
    }
}

fun randomComposeColor() : Color {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)
    return lerp(colors.random(), Color.White, Random.nextFloat() * 0.3f)
}

