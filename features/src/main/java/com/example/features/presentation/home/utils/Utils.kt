package com.example.features.presentation.home.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun formatToRupiah(amount: String?): String {
    return try {
        val number = amount?.toDoubleOrNull() ?: 0.0
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        formatter.format(number)
    } catch (_: Exception) {
        "Rp 0"
    }
}

fun formatToFullDate(dateString: String, locale: Locale = Locale("id", "ID")): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        val dateInput = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", locale)
        outputFormat.format(dateInput!!)

    } catch (_: Exception) {
        "-"
    }
}

fun String.cleanInfo(): String {
    return this
        .replace("\r\n", " ")
        .replace("  ", " ")
        .trim()
}

fun String.toCleanBulletList(): List<String> {
    return this
        .split("\r\n", "\n")
        .map { it.trimStart('-', ' ', '\t') }
        .filter { it.isNotBlank() }
}

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

@Composable
fun BulletList(lines: List<String>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier.fillMaxWidth()
    ) {
        lines.forEach { line ->
            Text(
                text = "• $line",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


enum class EntityType(@StringRes val label: Int, @DrawableRes val icon: Int) {
    CONTRACTOR(R.string.contractor, R.drawable.ic_contractor),
    CONSULTANT(R.string.consultant, R.drawable.ic_consultant),
    DEVELOPER(R.string.developer, R.drawable.ic_developer)
}