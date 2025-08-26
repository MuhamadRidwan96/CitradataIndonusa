package com.example.domain.model

data class FavoriteProject(
    val idProject: Int,
    val lastUpdate: String,
    val idRecord: String,
    val project: String,
    val statProject: String,
    val category: String,
    val status: String,
    val location: String,
    val province: String
)

