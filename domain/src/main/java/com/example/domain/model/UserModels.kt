package com.example.domain.model

data class UserModels(
    val name: String,
    val email: String,
    val photoUrl: String,
    val isLoggedIn: Boolean = true
)
