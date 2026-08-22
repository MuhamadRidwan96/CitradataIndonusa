package com.example.data.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import retrofit2.HttpException
import retrofit2.Response
import kotlin.random.Random

fun <T> Response<T>.toResult(): Result<T> {

    return try {
            if (isSuccessful){
                body()?.let {
                    Result.success(it)
                }?: Result.failure(
                    IllegalStateException("Response body is null!")
                )
            }else {
                Result.failure(HttpException(this))
            }

    }catch (e: Exception){
        Result.failure(e)
    }
}

fun randomComposeColor() : Color {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)
    return lerp(colors.random(), Color.White, Random.nextFloat() * 0.3f)
}

