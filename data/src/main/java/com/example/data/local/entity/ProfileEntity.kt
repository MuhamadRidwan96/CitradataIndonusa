package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity (
    @PrimaryKey val idUser: String,
    val name: String,
    val fullName:String,
    val email: String,
    val photo: String,
    val roleName : String,
    val idUserMaster: String?,
    val idRole: String,
    val idProvince: String?,
    val idCity: String?,
    val username: String,
    val password: String,
    val address: String,
    val position: String,
    val company: String,
    val phone: String?,
    val note: String,
    val userStatus: String,
    val packageMemberType: String,
    val subscriptionFee: String,
    val totalFee: String,
    val counted: String,
    val website: String,
    val startDate: String,
    val endDate: String,
    val userType: String,
    val created: String,
    val createdBy: String,
    val updated: String,
    val updatedBy: String,
    val status: String
)