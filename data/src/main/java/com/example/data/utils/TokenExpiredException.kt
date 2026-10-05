package com.example.data.utils

import okio.IOException

class TokenExpiredException(
    message: String = "Session expired. Please login again.",
    cause: Throwable? = null
) : Exception(message, cause)

class NoInternetException(
    message: String = "No internet connected.",
    cause: Throwable? = null
): IOException(message,cause)

class DataNotFoundException(
    message: String = "Data Not Found!.",
    cause: Throwable? = null
) : Exception(message, cause)