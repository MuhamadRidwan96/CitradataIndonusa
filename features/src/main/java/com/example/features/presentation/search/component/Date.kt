package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.DatePickerTextField
import com.example.feature_login.R

@Composable
fun StartAndEndDate(
    startDate: String,
    endDate:String,
    onStartDateSelected: (String) -> Unit,
    onEndDateSelected: (String) -> Unit,
    onClearStartDate: () -> Unit,
    onClearEndDate: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DatePickerTextField(
            modifier = Modifier.weight(1f),
            selectedDate = startDate,
            onDateSelected = onStartDateSelected,
            onClearClicked = onClearStartDate,
            placeHolder = stringResource(R.string.start_date)
        )

        DatePickerTextField(
            modifier = Modifier.weight(1f),
            selectedDate = endDate,
            onDateSelected = onEndDateSelected,
            onClearClicked = onClearEndDate,
            placeHolder = stringResource(R.string.end_date)
        )
    }
}