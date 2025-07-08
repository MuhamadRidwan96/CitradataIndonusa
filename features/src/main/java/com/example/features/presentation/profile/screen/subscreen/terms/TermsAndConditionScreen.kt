package com.example.features.presentation.profile.screen.subscreen.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.AppTheme
import com.example.core_ui.component.TopAppBarWithBack
import com.example.feature_login.R

@Composable
fun TermsAndConditionScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.term_and_condition),
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .sizeIn(minHeight = 56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) { Text(stringResource(R.string.cancel)) }
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .sizeIn(minHeight = 56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) { Text(stringResource(R.string.i_agree)) }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTerms() {
    AppTheme {
        TermsAndConditionScreen(navController = rememberNavController())
    }
}