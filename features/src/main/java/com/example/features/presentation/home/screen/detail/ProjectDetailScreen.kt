package com.example.features.presentation.home.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.core_ui.component.StatusChip
import com.example.domain.response.ProjectPpr
import com.example.domain.response.TeamMember
import com.example.features.presentation.authentication.screen.signup.MyTopAppBar
import com.example.features.presentation.home.component.CurrencyText
import com.example.features.presentation.home.component.InfoItem
import com.example.features.presentation.home.component.SpecificationItem
import com.example.features.presentation.home.component.TextMain
import com.example.features.presentation.home.component.TitleSection
import com.example.features.presentation.home.screen.DetailViewmodel
import com.example.features.presentation.home.state.DetailState
import com.example.features.presentation.home.utils.BulletList
import com.example.features.presentation.home.utils.EntityType
import com.example.features.presentation.home.utils.cleanInfo
import com.example.features.presentation.home.utils.formatToFullDate
import com.example.features.presentation.home.utils.toCleanBulletList

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

@Composable
fun MainDetailProjectContent(
    modifier: Modifier = Modifier,
    status: DetailState

) {
    val dataStatus = status.project

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        InfoRow(
            projectRecord = dataStatus?.data?.project?.projectCode.orEmpty(),
            catProject = dataStatus?.data?.project?.buildingCategoryName.orEmpty()
        )

        StatusRow(
            status = dataStatus?.data?.project?.categoryName.orEmpty(),
            createDate = dataStatus?.data?.project?.created.orEmpty()
        )

        ProjectName(
            nameProject = dataStatus?.data?.project?.projectName.orEmpty(),
            date = dataStatus?.data?.project?.projectCreated.orEmpty(),
            city = dataStatus?.data?.project?.cityName.orEmpty(),
            province = dataStatus?.data?.project?.provinceName.orEmpty()
        )
        val notes = dataStatus?.data?.project?.note.orEmpty()
        val parsedNote = remember(notes){
            notes.toCleanBulletList()
        }

        ProjectOverview(
            budget = dataStatus?.data?.project?.budget.orEmpty(),
            sourceFunding = dataStatus?.data?.project?.sourceFund.orEmpty(),
            location = dataStatus?.data?.project?.location.orEmpty(),
            catProjects = dataStatus?.data?.project?.buildingCategoryName.orEmpty(),
            conSchedule = dataStatus?.data?.project?.constructionSchedule.orEmpty(),
            stage = dataStatus?.data?.project?.stage.orEmpty(),
            additionalInfo = { BulletList(parsedNote) },
            start = dataStatus?.data?.project?.monthYearStart.orEmpty(),
            end = dataStatus?.data?.project?.monthYearEnd.orEmpty(),
            expDate = dataStatus?.data?.project?.expiredDate.orEmpty(),
        )
    }
}


@Composable
private fun StatusRow(status: String, createDate: String) {
    val textStyle = MaterialTheme.typography.titleSmall
    val textColor = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.date_created),
            style = textStyle,
            color = textColor
        )
        Text(
            text = formatToFullDate(createDate),
            style = textStyle,
            color = textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        StatusChip(status = status)
    }
}

@Composable
private fun InfoRow(
    projectRecord: String,
    catProject: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(R.drawable.ic_apartement, projectRecord)
        Icon(
            painter = painterResource(R.drawable.ic_chevron_r),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = catProject,
            color = Color.Gray,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun ProjectName(
    nameProject: String,
    city: String,
    province: String,
    date: String
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = nameProject,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            IconText(R.drawable.ic_city, city)
            IconText(R.drawable.ic_category, province)
            IconText(R.drawable.ic_calendar, formatToFullDate(date))
        }
    }
}


@Composable
private fun ProjectOverview(
    budget: String,
    sourceFunding: String,
    location: String,
    catProjects: String,
    conSchedule: String,
    stage: String,
    additionalInfo: @Composable () -> Unit,
    start: String,
    end: String,
    expDate: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {


        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TitleSection(
                icon = R.drawable.ic_info,
                title = stringResource(R.string.project_overview)
            )

            InfoItem(title = stringResource(R.string.budget)) {
                CurrencyText(budget)
            }

            InfoItem(title = stringResource(R.string.funding_source)) {
                TextMain(sourceFunding)
            }

            InfoItem(title = stringResource(R.string.location)) {
                TextMain(location)
            }

            InfoItem(title = stringResource(R.string.cat_project)) {
                TextMain(catProjects)
            }
            InfoItem(title = stringResource(R.string.const_schedule)) {
                TextMain(conSchedule)
            }
            InfoItem(title = stringResource(R.string.stage)) {
                TextMain(stage)
            }
            InfoItem(title = stringResource(R.string.add_info)) {
                additionalInfo()
            }
            DateRow(
                start = start,
                end = end,
                expDate = expDate
            )
        }
    }
}

@Composable
fun DateRow(start: String, end: String, expDate: String) {

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            InfoItem(title = stringResource(R.string.month_start)) {}
            IconText(R.drawable.ic_calendar, start)
            Spacer(modifier = Modifier.weight(1f))
            InfoItem(title = stringResource(R.string.month_end)) {}
            IconText(R.drawable.ic_calendar, end)
        }
        InfoItem(title = stringResource(R.string.exp_date)) {
            TextMain(expDate)
        }
    }
}

@Composable
private fun SpecificationTechnicalComponent(
    modifier: Modifier = Modifier,
    status: DetailState
) {
    val specStatus = status.specification
    Column(modifier = modifier.padding(12.dp)) {
        TitleSection(
            R.drawable.ic_apartement,
            stringResource(R.string.project_specification)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(specStatus) { item ->
                    SpecificationItem(
                        title = item.key.trim(),
                        value = item.value.trim(),
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressProjectComponent(
    modifier: Modifier = Modifier,
    status: DetailState
) {
    val statusProgress = status.ppr
    Column(modifier = modifier.padding(16.dp)) {
        TitleSection(
            R.drawable.ic_schedule,
            stringResource(R.string.report_progress)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            statusProgress.forEach { item ->
                ProgressReportCard(item)
            }
        }
    }
}


@Composable
fun ProgressReportCard(project: ProjectPpr) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .background(Color(0xFF0066FF))
            )

            // Content
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = project.pprCode,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    StatusChip(status = project.categoryName)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = formatToFullDate(project.pprCreated),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = project.newInfo.cleanInfo(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
private fun EntityCard(
    @DrawableRes icon: Int,
    section: String,
    name: String,
    address: String,
    phone: String,
    email: String,
    web: String,
    fax: String,
    note: String,
    teamMembers: List<TeamMember>,
    modifier: Modifier = Modifier

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {

            TitleSection(icon, section)
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            IconText(R.drawable.ic_phone, phone)
            IconText(R.drawable.ic_mail, email)
            IconText(R.drawable.ic_web, web)
            IconText(R.drawable.ic_fax, fax)

            if (teamMembers.isNotEmpty()) {
                Spacer(modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.team_member),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier.height(4.dp))
                teamMembers.forEach { member ->
                    TeamMemberComponent(member)
                    Spacer(modifier.height(8.dp))
                }
            }
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TeamMemberComponent(teamMember: TeamMember) {
    Text(
        text = teamMember.structureName,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth()
    )
    if (teamMember.position.isNotBlank()) {
        Text(
            text = teamMember.position,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
    if (teamMember.phone.isNotBlank()) {
        IconText(R.drawable.ic_phone, teamMember.phone)
    }
    if (teamMember.email.isNotBlank()) {
        IconText(R.drawable.ic_mail, teamMember.email)
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun FullScreenError(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}



