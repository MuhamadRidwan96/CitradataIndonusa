package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun LoginButtonSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isSubmitEnabled: Boolean,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit

) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        LoginButton(
            isLoading = isLoading,
            isSubmitEnabled = isSubmitEnabled,
            onLoginClick = onLoginClick
        )
        TextDivider()

        OutlinedButtonSign(
            onGoogleClick = onGoogleClick
        )
    }
}

@Composable
private fun LoginButton(
    isLoading: Boolean,
    isSubmitEnabled: Boolean,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onLoginClick,
        enabled = isSubmitEnabled
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
private fun TextDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Or",
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun OutlinedButtonSign(onGoogleClick: () -> Unit, modifier: Modifier = Modifier) {
    val painter = remember { R.drawable.google }

    OutlinedButton(
        onClick = onGoogleClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(
                painter = painterResource(painter),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified // Preserve original colors
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.login_with_google),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
        }
    }
}
