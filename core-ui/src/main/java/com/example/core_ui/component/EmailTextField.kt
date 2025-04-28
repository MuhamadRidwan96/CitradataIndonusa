package com.example.core_ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.core_ui.Shapes

@Composable
fun EmailTextField(
    value: String,
    isError: Boolean,
    label: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {}),
        isError = isError
    )
}
