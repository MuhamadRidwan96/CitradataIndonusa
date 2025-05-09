package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.feature_login.R
import com.example.features.presentation.profile.screen.ProfileItem

@Composable
fun ProfileContent(
    onEditClick: () -> Unit
) {

    // Stacked Profile Image with Edit Button
    Box(
        modifier = Modifier
            .size(112.dp),
        contentAlignment = Alignment.Center
    ) {
        // Profile Image
        Icon(
            imageVector = Icons.Default.AccountCircle, // ✅ Ikon ringan bawaan vector
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        // Edit Icon
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset((-16).dp, (-16).dp) // more adjustable
                .size(18.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}


@Composable
fun TextName(
    name: String,
    username: String
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.size(24.dp))
        // Name
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = username,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProfileItemRow(
    item: ProfileItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = when (item) {
                    is ProfileItem.Logout -> item.enabled
                    else -> true
                },
                onClick = item.onClick
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = when (item) {
                is ProfileItem.Logout -> Color.Gray
                else -> MaterialTheme.colorScheme.primary
            }
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.title,
            color = when (item) {
                is ProfileItem.Logout -> Color.Gray
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun ContentProfile(
    onMembershipClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onLogout: (() -> Unit)? = null
) {
    Column {

        ProfileItemRow(
            item = ProfileItem.Regular(
                icon = Icons.Default.WorkspacePremium,
                title = stringResource(R.string.membership),
                onClick = onMembershipClick
            )
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )


        ProfileItemRow(
            item = ProfileItem.Regular(
                icon = Icons.Default.Phone,
                title = stringResource(R.string.call_us),
                onClick = onContactUsClick
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        ProfileItemRow(
            item = ProfileItem.Regular(
                icon = Icons.Default.LocalPolice,
                title = stringResource(R.string.privacy_policy),
                onClick = onPrivacyPolicyClick
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        ProfileItemRow(
            ProfileItem.Regular(
                icon = Icons.Default.ContentPaste,
                title = stringResource(R.string.term_and_condition),
                onClick = onTermsClick
            )
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        ProfileItemRow(
            item = ProfileItem.Logout(
                onClick = { onLogout?.invoke() },
                enabled = onLogout != null
            )
        )
    }
}
