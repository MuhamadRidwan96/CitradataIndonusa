package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.core_ui.component.StatusChip
import com.example.features.presentation.home.state.DetailState
import com.example.features.presentation.home.utils.BulletList
import com.example.features.presentation.home.utils.formatToFullDate
import com.example.features.presentation.home.utils.toCleanBulletList

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
        val parsedNote = remember(notes) {
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
private fun DateRow(start: String, end: String, expDate: String) {

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