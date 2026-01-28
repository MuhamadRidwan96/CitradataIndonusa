package com.example.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    imeAction: ImeAction,
    onImeAction: () -> Unit,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,

    ) {
    Column(
        modifier = modifier
    ) {

        Text(text = label, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onAny = { onImeAction() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(48.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
        )
    }
}
/*

@Preview(
    name = "Multiple Text Fields",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    showSystemUi = true
)
@Composable
fun LabeledTextFieldMultiplePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var username by remember { mutableStateOf("johndoe") }
            var email by remember { mutableStateOf("john@example.com") }
            var phone by remember { mutableStateOf("+628123456789") }

            LabeledTextField(
                label = "Username",
                value = username,
                onValueChange = { username = it },
                imeAction = ImeAction.Next,
                onImeAction = { */
/* Move to next field *//*
 }
            )

            LabeledTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                imeAction = ImeAction.Next,
                onImeAction = { */
/* Move to next field *//*
 },
                keyboardType = KeyboardType.Email
            )

            LabeledTextField(
                label = "Phone Number",
                value = phone,
                onValueChange = { phone = it },
                imeAction = ImeAction.Done,
                onImeAction = { */
/* Submit form *//*
 },
                keyboardType = KeyboardType.Phone
            )
        }
    }
}*/
