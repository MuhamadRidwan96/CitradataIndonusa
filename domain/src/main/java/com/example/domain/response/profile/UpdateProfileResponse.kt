package com.example.domain.response.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("success")
    val success:Boolean,

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message:String
)