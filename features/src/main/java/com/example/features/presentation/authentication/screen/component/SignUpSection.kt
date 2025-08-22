package com.example.features.presentation.authentication.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R

@Composable
fun SignUpSection(
    onSignUpClick: () -> Unit
) {
    val textStyle = TextStyle(fontSize = 14.sp)

    val rowModifier = remember {
        Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        Text(
            text =stringResource(R.string.have_account),
            style = textStyle
        )

        TextButton(onClick = onSignUpClick) {
            Text(
                text =stringResource(R.string.sign_up),
                style = textStyle
            )
        }
    }
}
