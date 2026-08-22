package com.example.features.presentation.home.utils

import com.example.data.local.entity.FavoriteProjectEntity
import com.example.domain.model.FilterDataModel
import com.example.domain.model.Project
import com.example.features.presentation.favorite.state.FavoriteProjectUiState
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.state.search.ProjectUiItem

fun Project.toProjectUiItem(index : Int,isFavorite: Boolean = false): ProjectUiItem {
    return ProjectUiItem(
        checkbox = this.checkbox,
        no = index,
        lastUpdate = this.lastUpdate,
        idRecord = this.idRecord,
        idProject = this.idProject.toInt(),
        project = this.project,
        statProject = this.statProject,
        category = this.category,
        status = this.status,
        location = this.location,
        province = this.province,
        isFavorite = isFavorite
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

fun FavoriteProjectUiState.toDataState(no: Int = 0): ProjectUiItem {
    return ProjectUiItem(
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

fun ProjectUiItem.toFavoriteProjectEntity(): FavoriteProjectEntity {
    return FavoriteProjectEntity(
        idProject = this.idProject ?: 0,
        lastUpdate = this.lastUpdate ?: "",
        idRecord = this.idRecord ?: "",
        project = this.project ?: "",
        statProject = this.statProject ?: "",
        category = this.category ?: "",
        status = this.status ?: "",
        location = this.location ?: "",
        province = this.province ?: ""
    )
}