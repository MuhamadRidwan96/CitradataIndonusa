package com.example.features.presentation.home.state

import androidx.compose.runtime.Immutable
import com.example.domain.response.Consultant
import com.example.domain.response.Contractor
import com.example.domain.response.Developer
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.ProjectPpr
import com.example.domain.response.ProjectSpecification
import com.example.domain.response.ProjectUpdateStatus

@Immutable
data class DetailState(
    val error : String? = null,
    val isLoading: Boolean = false,
    val message: String? = null,
    val project: ProjectDetailResponse? = null,
    val developer: List<Developer> = emptyList(),
    val contractor: List<Contractor> = emptyList(),
    val consultant: List<Consultant> = emptyList(),
    val specification: List<ProjectSpecification> = emptyList(),
    val ppr: List<ProjectPpr> = emptyList(),
    val updateStatus: List<ProjectUpdateStatus> = emptyList(),
    val showCp: Boolean = false,
    val showCpEmail: Boolean = false,
    val showCpPhone: Boolean = false
)
