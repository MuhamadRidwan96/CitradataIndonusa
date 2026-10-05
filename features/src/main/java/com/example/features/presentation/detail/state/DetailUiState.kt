package com.example.features.presentation.detail.state

import androidx.compose.runtime.Immutable
import com.example.core_ui.architecture.base.BaseUiState
import com.example.domain.response.data.Consultant
import com.example.domain.response.data.Contractor
import com.example.domain.response.data.Developer
import com.example.domain.response.data.ProjectDetailResponse
import com.example.domain.response.data.ProjectPpr
import com.example.domain.response.data.ProjectSpecification
import com.example.domain.response.data.ProjectUpdateStatus
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
