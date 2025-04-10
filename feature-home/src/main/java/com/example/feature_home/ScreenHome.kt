package com.example.feature_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.CitraDataIndonusaTheme
import com.example.navigation.LocalAppNavigator

@Composable
fun ScreenHome(
    modifier: Modifier = Modifier,
    viewModel: LogOutViewModel = hiltViewModel()
) {
    val navigator = LocalAppNavigator.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(id = R.string.Greeting),
            modifier = modifier.padding(horizontal = 8.dp), // Give space from Divider
            color = Color.Gray
        )

        Button(onClick = {
            viewModel.logout()
            navigator.navigateBackToLogin()
        }) {
            Text(stringResource(R.string.Logout))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview1() {
    CitraDataIndonusaTheme {
        ScreenHome()
    }
}