package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.DatePickerTextField
import com.example.feature_login.R

@Composable
fun StartAndEndDate() {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        DatePickerTextField(
            modifier = Modifier.weight(1f),
            selectedDate = startDate,
            onDateSelected = { date -> startDate = date },
            onClearClicked = { startDate = "" },
            placeHolder = stringResource(R.string.start_date)
        )

        DatePickerTextField(
            modifier = Modifier.weight(1f),
            selectedDate = endDate,
            onDateSelected = { date -> endDate = date },
            onClearClicked = { endDate = "" },
            placeHolder = stringResource(R.string.end_date)
        )
    }
}