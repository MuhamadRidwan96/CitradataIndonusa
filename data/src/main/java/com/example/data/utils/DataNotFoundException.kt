package com.example.data.utils

class DataNotFoundException(
    message: String = "Data Not Found!.",
    cause: Throwable? = null
) : Exception(message, cause)
