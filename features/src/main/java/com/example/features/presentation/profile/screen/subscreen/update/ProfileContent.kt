package com.example.features.presentation.profile.screen.subscreen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.feature_login.R

@Composable
fun ProfileContent(
    onEditClick: () -> Unit
) {

    val profileIconSize = remember { 96.dp }
    val editButtonOffset = remember { (-16).dp }
    val editButtonSize = remember { 18.dp }
    val editIconSize = remember { 15.dp }

    // Stacked Profile Image with Edit Button
    Box(
        modifier = Modifier
            .size(112.dp),
        contentAlignment = Alignment.Center
    ) {
        // Profile Image
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(profileIconSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        // Edit Icon
        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(editButtonOffset, editButtonOffset) // more adjustable
                .size(editButtonSize)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(editIconSize)
            )
        }
    }
}


@Composable
fun TextName(
    name: String,
    username: String
) {
    val spacer = remember { 24.dp }
    val padding = remember { 16.dp }

    val nameStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
    )

    val usernameStyle = MaterialTheme.typography.titleMedium.copy(
        color = Color.Gray
    )
    Column(
        modifier = Modifier.padding(padding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.size(spacer))
        // Name
        Text(
            text = name,
            style = nameStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = username,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            color = Color.Gray,
            maxLines = 1,
            style = usernameStyle
        )
    }
}

@Composable
fun ProfileItemRow(
    item: ProfileItem,
    modifier: Modifier = Modifier
) {
    val rowPadding = remember { PaddingValues(vertical = 12.dp, horizontal = 16.dp) }
    val iconSpacer = remember { 12.dp }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = item.enabled,
                onClick = item.onClick
            )
            .padding(rowPadding),
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
        Spacer(modifier = Modifier.width(iconSpacer))

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
private fun ProfileDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}


@Composable
fun ContentProfile(
    onMembershipClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onLogout: (() -> Unit)? = null
) {
    val items = listOf(
        ProfileItem.Regular(Icons.Default.WorkspacePremium, stringResource(R.string.membership), onMembershipClick),
        ProfileItem.Regular(Icons.Default.Phone, stringResource(R.string.call_us), onContactUsClick),
        ProfileItem.Regular(Icons.Default.LocalPolice, stringResource(R.string.privacy_policy), onPrivacyPolicyClick),
        ProfileItem.Regular(Icons.Default.ContentPaste, stringResource(R.string.term_and_condition), onTermsClick)
    ) + if (onLogout != null) {
        listOf(ProfileItem.Logout(onLogout, true))
    } else emptyList()

    LazyColumn(
        modifier = Modifier
            .size(650.dp)
    ){
        itemsIndexed(items) { index, item ->
            ProfileItemRow(item)
            if (index < items.lastIndex) {
                ProfileDivider()
            }
        }
    }
}

@Stable
sealed interface ProfileItem {
    val icon: ImageVector
    val title: String
    val onClick: () -> Unit
    val enabled: Boolean
        get() = true

    data class Regular(
        override val icon: ImageVector,
        override val title: String,
        override val onClick: () -> Unit
    ) : ProfileItem

    data class Logout(
        override val onClick: () -> Unit,
        override val enabled: Boolean
    ) : ProfileItem {
        override val icon: ImageVector = Icons.AutoMirrored.Filled.Logout
        override val title: String = ProfileItemConstant.LOGOUT
    }
}

object ProfileItemConstant {
    const val LOGOUT = "Logout"
}

