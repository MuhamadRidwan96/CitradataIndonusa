package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.LabeledTextField
import com.example.features.presentation.profile.screen.state.EditProfile

@Composable
fun UpdateProfileComponent(
    profileState: EditProfile,
    usernameChange: (String) -> Unit,
    nameChange: (String) -> Unit,
    emailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onUpdateClick: () -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier
            .imePadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            ProfileEditHeader(
                name = profileState.name
            )
        }

        item {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    ProfileTextField(
                        label = stringResource(R.string.username),
                        value = profileState.username,
                        onValueChange = usernameChange,
                        imeAction = ImeAction.Next,
                        onImeAction = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )

                    ProfileTextField(
                        label = stringResource(R.string.full_name),
                        value = profileState.fullName,
                        onValueChange = nameChange,
                        imeAction = ImeAction.Next,
                        onImeAction = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )

                    ProfileTextField(
                        label = stringResource(R.string.email),
                        value = profileState.email,
                        onValueChange = emailChange,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                        onImeAction = {
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }

        item {

            UpdateButton(
                onClick = {
                    focusManager.clearFocus()
                    onUpdateClick()
                }
            )
        }
    }
}

@Composable
private fun ProfileEditHeader(
    name: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(110.dp),
            shape = CircleShape,
            tonalElevation = 4.dp
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.update_profile),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onImeAction: () -> Unit,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction,
    keyboardType: KeyboardType = KeyboardType.Text,

    ) {
    LabeledTextField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        imeAction = imeAction,
        keyboardType = keyboardType,
        onImeAction = onImeAction
    )
}

@Composable
private fun UpdateButton(
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.update_profile)
        )
    }
}



