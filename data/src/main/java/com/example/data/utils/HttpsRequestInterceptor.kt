package com.example.data.utils

import com.example.data.preferencesImpl.UserPreferencesImpl
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpsRequestInterceptor @Inject constructor(private val userPreferenceImpl: UserPreferencesImpl) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
           userPreferenceImpl.getSession().firstOrNull()?.token
        } ?:throw IllegalArgumentException("Token is not available. Please login first.")
        val request = chain.request().newBuilder()
            .addHeader("Auth-Token", token)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}
