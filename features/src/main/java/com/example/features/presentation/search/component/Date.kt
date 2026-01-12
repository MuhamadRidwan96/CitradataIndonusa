package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.DatePickerTextField
import com.example.feature_login.R

@Composable
fun StartAndEndDate(
    modifier:Modifier = Modifier,
    startDate: String,
    endDate: String,
    onStartDateSelect: (String) -> Unit,
    onEndDateSelect: (String) -> Unit,
    onClearStartDate: () -> Unit,
    onClearEndDate: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(com.example.core_ui.R.drawable.calendar),
                contentDescription = "date",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.timeline),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {

            DatePickerTextField(
                modifier = Modifier.weight(1f),
                selectedDate = startDate,
                onDateSelect = onStartDateSelect,
                onClearClick = onClearStartDate,
                placeholder = stringResource(R.string.start_date)
            )

            DatePickerTextField(
                modifier = Modifier.weight(1f),
                selectedDate = endDate,
                onDateSelect = onEndDateSelect,
                onClearClick= onClearEndDate,
                placeholder = stringResource(R.string.end_date)
            )
        }
    }
}