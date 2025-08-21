package com.example.features.presentation.home.screen.detail

import EntityCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.features.presentation.authentication.screen.signup.MyTopAppBar
import com.example.features.presentation.home.component.FullScreenError
import com.example.features.presentation.home.component.FullScreenLoading
import com.example.features.presentation.home.component.MainDetailProjectContent
import com.example.features.presentation.home.component.ProgressProjectComponent
import com.example.features.presentation.home.component.SpecificationTechnicalComponent
import com.example.features.presentation.home.screen.DetailViewmodel
import com.example.features.presentation.home.utils.EntityType

@Composable
fun ProjectDetailScreen(
    projectId: String,
    onBackClick: () -> Unit,
    viewModel: DetailViewmodel = hiltViewModel()
) {
    val dataState by viewModel.dataState.collectAsState()

    LaunchedEffect(projectId) {
        viewModel.fetchDetailData(projectId)
    }

    Scaffold(
        topBar = { MyTopAppBar(onBackClick = { onBackClick() }) },
        content = { paddingValues ->
            when {
                dataState.isLoading -> {
                    FullScreenLoading()
                }

                dataState.error != null -> {
                    FullScreenError(
                        error = dataState.error ?: "Unknown Error",
                        onRetry = { viewModel.fetchDetailData(projectId) })
                }

                else -> {
                    val contentModifier = Modifier
                        .fillMaxSize()

                    LazyColumn(
                        modifier = contentModifier, contentPadding = PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding() + 70.dp
                        )
                    ) {
                        item { MainDetailProjectContent(status = dataState) }
                        item { SpecificationTechnicalComponent(status = dataState) }
                        item { ProgressProjectComponent(status = dataState) }

                        //Developer
                        items(dataState.developer) { dev ->
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
                        items(dataState.contractor) { contractor ->
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
                        items(dataState.consultant) { consultant ->
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
        })
}














