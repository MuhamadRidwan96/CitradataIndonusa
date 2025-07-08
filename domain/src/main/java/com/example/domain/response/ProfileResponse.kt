package com.example.domain.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse (
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data : UserData
)

data class UserData(
    @SerializedName("iduser")
    val idUser: String,

    @SerializedName("iduser_master")
    val idUserMaster: String?,

    @SerializedName("idrole")
    val idRole: String,

    @SerializedName("idprovince")
    val idProvince: String?,

    @SerializedName("idcity")
    val idCity: String?,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("position")
    val position: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("note")
    val note: String,

    @SerializedName("user_status")
    val userStatus: String,

    @SerializedName("package_member_type")
    val packageMemberType: String,

    @SerializedName("subscription_fee")
    val subscriptionFee: String,

    @SerializedName("total_fee")
    val totalFee: String,

    @SerializedName("counted")
    val counted: String,

    @SerializedName("website")
    val website: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("user_type")
    val userType: String,

    @SerializedName("created")
    val created: String,

    @SerializedName("createdby")
    val createdBy: String,

    @SerializedName("updated")
    val updated: String,

    @SerializedName("updatedby")
    val updatedBy: String,

    @SerializedName("status")
    val status: String
)
