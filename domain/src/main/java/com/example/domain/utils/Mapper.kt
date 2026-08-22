package com.example.domain.utils

import com.example.domain.model.Project
import com.example.domain.response.RecordData

fun RecordData.toDomain(): Project{
    return Project(
        checkbox = checkbox,
        no = no,
        lastUpdate = lastUpdate,
        idRecord = idRecord,
        idProject = idProject,
        project = project,
        statProject = statProject,
        category = category,
        status = status,
        location = location,
        province = province
    )
}

