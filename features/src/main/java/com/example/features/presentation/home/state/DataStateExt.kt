package com.example.features.presentation.home.state

import com.example.domain.response.RecordData

fun RecordData.toDataState(isFavorite: Boolean = false):DataState{
    return DataState(
        checkbox = this.checkbox,
        no = this.no,
        lastUpdate = this.lastUpdate,
        idRecord = this.idRecord,
        idProject = this.idProject,
        project = this.project,
        statProject = this.statProject,
        category = this.category,
        status = this.status,
        location = this.location,
        province = this.province,
        isFavorite = isFavorite
    )
}