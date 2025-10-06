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
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.domain.model.FavoriteProject
import com.example.domain.model.FilterDataModel
import com.example.features.presentation.home.state.DataState
import com.example.features.presentation.search.state.ProjectFilterState
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

@Composable
fun BulletList(lines: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()
    ) {
        lines.forEach { line ->
            Text(
                text = "• $line",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

fun Map<String, String>.toFilterDataModel(): FilterDataModel {
    return FilterDataModel(
        startDate = this["start_date"] ?: "",
        endDate = this["end_date"] ?: "",
        idProject = this["idproject"] ?: "",
        projectName = this["project_name"] ?: "",
        idProjectCategory = this["idproject_category"] ?: "",
        idBuildingCategory = this["idbuilding_category"] ?: "",
        address = this["address"] ?: "",
        idProvince = this["idprovince"] ?: "",
        idCity = this["idcity"] ?: "",
        idDeveloper = this["iddeveloper"] ?: "",
        idConsultant = this["idconsultant"] ?: "",
        idConsultantCategory = this["idconsultant_Category"] ?: "",
        idContractor = this["idcontractor"] ?: "",
        idContractorCategory = this["idcontractor_category"] ?: "",
        idProjectStatusCategory = this["idproject_status_category"] ?: "",
        withPpr = this["with_ppr"] ?: "",
        idSectorCategory = this["idsector_category"] ?: "",
        province = this["province"] ?: "",
    )
}

fun DataState.toFavoriteProjectEntity(): FavoriteProjectEntity {
    return FavoriteProjectEntity(
        idProject = this.idProject.toInt(),
        lastUpdate = this.lastUpdate,
        idRecord = this.idRecord,
        project = this.project,
        statProject = this.statProject,
        category = this.category,
        status = this.status,
        location = this.location,
        province = this.province
    )
}

fun ProjectFilterState.toFilterDataModel(): FilterDataModel {
    return FilterDataModel(
        startDate = if (startDate.isEmpty()) "" else startDate,
        endDate = if (endDate.isEmpty()) "" else endDate,
        idProject = if (idProject.isEmpty()) "" else idProject,
        projectName = if (projectName.isEmpty()) "" else projectName,
        idProjectCategory = (idProjectCategory ?: "").toString(),
        idBuildingCategory = (idBuildingCategory ?: "").toString(),
        address = if (address.isEmpty()) "" else address,
        idProvince = idProvince ?: "",
        idCity = idCity ?: "",
        idDeveloper = (idDeveloper ?: "").toString(),
        idConsultant = if (idConsultant.isEmpty()) "" else idConsultant,
        idConsultantCategory = idConsultantCategory,
        idContractor = idContractor,
        idContractorCategory = idContractorCategory,
        idSectorCategory = idSectorCategory,
        idProjectStatusCategory = (idProjectStatusCategory ?: "").toString(),
        withPpr = if (withPpr) "PPR" else "",
        province = provinceName
    )
}

fun FavoriteProject.toDataState(no: Int = 0): DataState {
    return DataState(
        no = no,
        idProject = this.idProject,
        lastUpdate = this.lastUpdate,
        idRecord = this.idRecord,
        project = this.project,
        statProject = this.statProject,
        category = this.category,
        status = this.status,
        location = this.location,
        province = this.province,
    )
}

enum class EntityType(@StringRes val label: Int, @DrawableRes val icon: Int) {
    CONTRACTOR(R.string.contractor, R.drawable.ic_contractor),
    CONSULTANT(R.string.consultant, R.drawable.ic_consultant),
    DEVELOPER(R.string.developer, R.drawable.ic_developer)
}