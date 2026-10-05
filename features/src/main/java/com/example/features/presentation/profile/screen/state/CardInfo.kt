package com.example.features.presentation.profile.screen.state

import androidx.compose.runtime.Immutable


@Immutable
data class CardInfo(
    val name: String,
    val fullName: String,
    val email: String,
    val address: String,
    val company: String,
    val phone: String?,
    val dateEnd: String,
    val username : String
)

@Immutable
data class EditProfile(
    val name: String,
    val fullName: String,
    val email: String,
    val username : String
)