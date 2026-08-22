package com.example.features.presentation.detail.state

import androidx.compose.runtime.Immutable
import com.example.core_ui.architecture.base.BaseUiState
import com.example.domain.response.Consultant
import com.example.domain.response.Contractor
import com.example.domain.response.Developer
import com.example.domain.response.ProjectDetailResponse
import com.example.domain.response.ProjectPpr
import com.example.domain.response.ProjectSpecification
import com.example.domain.response.ProjectUpdateStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class DetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,

    val project: ProjectDetailResponse? = null,

    val developer: ImmutableList<Developer> = persistentListOf(),
    val contractor: ImmutableList<Contractor> = persistentListOf(),
    val consultant: ImmutableList<Consultant> = persistentListOf(),

    val specification: ImmutableList<ProjectSpecification> = persistentListOf(),
    val ppr: ImmutableList<ProjectPpr> = persistentListOf(),
    val updateStatus: ImmutableList<ProjectUpdateStatus> = persistentListOf(),

    val showCp: Boolean = false,
    val showCpEmail: Boolean = false,
    val showCpPhone: Boolean = false
) : BaseUiState
