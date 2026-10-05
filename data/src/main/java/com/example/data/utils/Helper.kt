package com.example.data.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.random.Random

fun <T> Response<T>.toResult(): Result<T> {

    return when {
        isSuccessful -> {
            body()?.let {
                Result.success(it)
            } ?: Result.failure(
                IllegalStateException("Response body is null!")
            )
        }
        code() == 401 -> {
            Result.failure(
                TokenExpiredException(
                    "Token Expired, Silahkan login kembali!"
                )
            )
        } else -> {
            Result.failure(
                HttpException(this)
            )
        }
    }
}


fun randomComposeColor(): Color {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)
    return lerp(colors.random(), Color.White, Random.nextFloat() * 0.3f)
}

fun Throwable.isNetworkUnavailable(): Boolean {
    return this is UnknownHostException ||
            this is SocketTimeoutException ||
            this is ConnectException ||
            this is IOException
}

