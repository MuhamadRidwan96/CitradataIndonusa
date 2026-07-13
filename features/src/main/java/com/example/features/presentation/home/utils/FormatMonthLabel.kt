package com.example.features.presentation.home.utils

// Format "2025-06" → "Jun '25"
fun formatMonthLabel(month : String) : String {
    return try {
        val parts = month.split("-")
        val year = parts[0].takeLast(2)
        val monthNum = parts[1].toInt()
        val monthName = listOf(
            "Jan","Feb","Mar","Apr","May","Jun",
            "Jul","Aug","Sep","Oct","Nov","Dec"
        )
        "${monthName[monthNum-1]} '$year"
    } catch (_: Exception){
        month
    }

}