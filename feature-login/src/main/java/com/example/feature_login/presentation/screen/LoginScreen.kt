package com.example.feature_login.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature_login.R

@Composable
fun ScreenLogin(
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)

    Column(
        modifier = modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoImage()
        LoginLayout(
            isPassWordWrong = false,
            isUserNameWrong = false,
            username = "",
            password = ""
        )
        LoginButtons()
    }
}

@Composable
fun LogoImage() {
    Image(
        modifier = Modifier.size(150.dp),
        painter = painterResource(id = R.drawable.logo_dummy),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoginButtons() {

    val smallPadding = dimensionResource(id = R.dimen.padding_medium)
    Column(
        modifier = Modifier.padding(smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.login),
            onClick = { /*TODO*/ }
        )
        Text(
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        GoogleLoginButton()
        SignUpRow()
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun GoogleLoginButton() {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = { /*TODO*/ }
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Google Login"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(id = R.string.login_with_google),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black
        )
    }
}

@Composable
fun SignUpRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.have_account))
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
fun LoginLayout(
    isPassWordWrong: Boolean,
    isUserNameWrong: Boolean,
    username: String,
    password: String
) {
    val smallPadding = dimensionResource(id = R.dimen.padding_small)
    Column(
        verticalArrangement = Arrangement.spacedBy(smallPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(smallPadding)
    ) {
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.displayLarge
        )

        AuthTextField(
            value = username,
            isError = isUserNameWrong,
            label = if (isUserNameWrong) stringResource(id = R.string.wrong_username) else stringResource(id = R.string.username),
            leadingIcon = Icons.Default.Person
        )

        AuthTextField(
            value = password,
            isError = isPassWordWrong,
            label = if (isPassWordWrong) stringResource(id = R.string.wrong_password) else stringResource(id = R.string.password),
            leadingIcon = Icons.Default.Lock
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.forgot_account),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    isError: Boolean,
    label: String,
    leadingIcon: ImageVector
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        onValueChange = {},
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {}),
        isError = isError
    )
}
@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun LoginScreenView() {
    CitraDataIndonusaTheme {
        ScreenLogin()
    }




}