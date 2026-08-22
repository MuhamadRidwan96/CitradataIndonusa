package com.example.features.presentation.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.features.presentation.authentication.screen.signup.component.MyTopAppBar
import com.example.features.presentation.home.component.EntityCard
import com.example.features.presentation.home.component.FullScreenLoading
import com.example.features.presentation.home.component.MainDetailProjectContent
import com.example.features.presentation.home.component.ProgressProjectComponent
import com.example.features.presentation.home.component.SpecificationTechnicalComponent
import com.example.features.presentation.home.utils.EntityType
import com.example.features.presentation.detail.DetailViewmodel

@Composable
fun ProjectDetailScreen(
    modifier: Modifier = Modifier,
    projectId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewmodel = hiltViewModel()
) {
    val dataState by viewModel.uiState.collectAsState()

    LaunchedEffect(projectId) {
        viewModel.fetchDetailData(projectId)
    }
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                MyTopAppBar(
                    onBackClick = { onBackClick() },
                    text = "Back"
                )
            }
        ) { paddingValues ->
            when {
                dataState.isLoading -> {
                    FullScreenLoading()
                }

                dataState.error != null -> {
                   /* FullScreenError(
                        error = dataState.error ?: "Unknown Error",
                        onRetry = { viewModel.fetchDetailData(projectId) })*/
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(bottom = 70.dp)
                    ) {
                        item(contentType = "project") {
                            MainDetailProjectContent(status = dataState)
                        }
                        item(contentType = "specification") {
                            SpecificationTechnicalComponent(
                                status = dataState
                            )
                        }
                        item(contentType = "progress") { ProgressProjectComponent(status = dataState) }

                        //Developer
                        items(
                            items = dataState.developer,
                            key = { dev -> dev.name },
                            contentType = { "developer" }
                        ) { dev ->
                            EntityCard(
                                icon = EntityType.DEVELOPER.icon,
                                section = stringResource(EntityType.DEVELOPER.label),
                                name = dev.name,
                                address = dev.address,
                                phone = dev.phone,
                                email = dev.email,
                                web = dev.website,
                                fax = dev.fax,
                                note = dev.note,
                                teamMembers = dev.team
                            )
                        }

                        //Contractor
                        items(
                            items = dataState.contractor,
                            key = { con -> con.name },
                            contentType = { "contractor" }) { contractor ->
                            EntityCard(
                                icon = EntityType.CONTRACTOR.icon,
                                section = stringResource(EntityType.CONTRACTOR.label),
                                name = contractor.name,
                                address = contractor.address,
                                phone = contractor.phone,
                                email = contractor.email,
                                web = contractor.website,
                                fax = contractor.website,
                                note = contractor.note,
                                teamMembers = contractor.team
                            )
                        }

                        //Consultant
                        items(
                            items = dataState.consultant,
                            key = { cons -> cons.name },
                            contentType = { "consultant" }) { consultant ->
                            EntityCard(
                                icon = EntityType.CONSULTANT.icon,
                                section = stringResource(EntityType.CONSULTANT.label),
                                name = consultant.name,
                                address = consultant.address,
                                phone = consultant.phone,
                                email = consultant.email,
                                web = consultant.website,
                                fax = consultant.fax,
                                note = consultant.note,
                                teamMembers = consultant.team
                            )
                        }
                    }
                }
            }
        }
    }
}














