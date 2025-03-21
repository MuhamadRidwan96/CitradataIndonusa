package com.example.data.utils

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
           userPreferences.getSession().firstOrNull()?.token
        } ?:throw IllegalArgumentException("Token is not available. Please login first.")
        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()
        return chain.proceed(request)
    }

}
