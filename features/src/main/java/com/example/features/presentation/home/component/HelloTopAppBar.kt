package com.example.features.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import com.example.features.presentation.home.screen.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource

@Composable
fun ProfileHeaders(
    modifier: Modifier = Modifier,
    homeVm : HomeViewModel = hiltViewModel()

) {

    val profile by homeVm.uiState.collectAsStateWithLifecycle()

    val updateStyle = MaterialTheme.typography.titleLarge
        .copy(color = MaterialTheme.colorScheme.onSurface)


    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Text(text = stringResource(com.example.feature_login.R.string.hello), style = updateStyle)
            Text(text = profile.user?.name ?: "", style = updateStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Image(
                painter = painterResource(R.drawable.waving_hand),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
    }

}