package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(

    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email :String,
    @SerializedName("password")
    val password: String

)
