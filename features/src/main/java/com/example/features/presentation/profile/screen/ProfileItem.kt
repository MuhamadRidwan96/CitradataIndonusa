package com.example.features.presentation.profile.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ProfileItem {
    abstract val icon : ImageVector
    abstract val title:String
    abstract val onClick: () -> Unit

    data class Regular(
        override val icon: ImageVector,
        override val title: String,
        override val onClick: () -> Unit
    ):ProfileItem()

    data class Logout(
        override val icon : ImageVector = Icons.AutoMirrored.Default.Logout,
        override val title: String = ProfileItemConstant.LOGOUT,
        override val onClick: () -> Unit,
        val enabled : Boolean = true
    ):ProfileItem()


}

object ProfileItemConstant{
    const val LOGOUT = "Logout"
}