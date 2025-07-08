package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("token") val token: String,
    val isLogin: Boolean = false
)