package com.example.data.utils

import com.example.data.preferencesImpl.UserPreferencesImpl
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpsRequestInterceptor @Inject constructor(
    private val userPreferences: UserPreferencesImpl
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val token = runBlocking {
            userPreferences
                .getSession()
                .firstOrNull()
                ?.token
        }

        val request = chain
            .request()
            .newBuilder()
            .addHeader(
                "Content-Type",
                "application/json"
            )
            .addHeader(
                "Accept",
                "application/json"
            )
            .apply {

                if (!token.isNullOrBlank()) {
                    addHeader(
                        "Auth-Token",
                        token
                    )
                }
            }
            .build()

        return chain.proceed(request)
    }
}