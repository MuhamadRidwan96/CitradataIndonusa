package com.example.data.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(


    @SerializedName("success")
    val success:Boolean,

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: TokenData
)

data class TokenData(

    @field:SerializedName("token")
    val token: String,

)


