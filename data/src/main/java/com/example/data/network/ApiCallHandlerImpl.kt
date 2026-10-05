package com.example.data.network

import android.util.Log
import com.example.data.utils.NetworkEventManager
import com.example.data.utils.NoInternetException
import com.example.data.utils.TokenExpiredException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiCallHandlerImpl @Inject constructor(
    private val networkEventManager: NetworkEventManager
) : ApiCallHandler {
    override suspend fun <T> execute(
        apiCall: suspend () -> Result<T>
    ): Result<T> {

        return try {

            val result = apiCall()

            Log.d(
                "ApiCallHandler",
                "success = ${result.isSuccess}"
            )

            Log.d(
                "ApiCallHandler",
                "exception = ${result.exceptionOrNull()}"
            )

            Log.d(
                "ApiCallHandler",
                "exceptionType = ${
                    result.exceptionOrNull()?.javaClass?.name
                }"
            )

            result.exceptionOrNull()?.let { exception ->

                if (exception is TokenExpiredException) {

                    Log.d(
                        "NetworkEvent",
                        "Emitting TokenExpired"
                    )

                    networkEventManager.emit(
                        NetworkEvent.TokenExpired
                    )
                }
            }

            result

        } catch (e: IOException) {

            Result.failure(
                NoInternetException(cause = e)
            )
        }
    }
}