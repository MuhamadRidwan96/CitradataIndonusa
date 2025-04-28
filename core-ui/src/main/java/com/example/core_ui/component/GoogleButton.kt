package com.example.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun GoogleLoginButton(onClick: () -> Unit,isLoading: Boolean) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        enabled = !isLoading
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google Login"
        )
        Spacer(modifier = Modifier.width(4.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text(
                text = stringResource(id = R.string.login_with_google),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}