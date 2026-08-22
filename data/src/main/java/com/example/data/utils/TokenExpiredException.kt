package com.example.data.utils

class TokenExpiredException(
    message: String = "Session expired. Please login again.",
    cause: Throwable? = null
) : Exception(message, cause)