package com.example.data.network

interface ApiCallHandler {

    suspend fun <T> execute(
        apiCall: suspend () -> Result<T>
    ): Result<T>
}