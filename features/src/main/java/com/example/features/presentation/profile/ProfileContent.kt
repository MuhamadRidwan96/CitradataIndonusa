package com.example.features.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.JakartaSans
import com.example.feature_login.R

@Composable
fun ProfileContent() {
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Stacked Profile Image with Edit Button
        Box(
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(124.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(68.dp)
                )
            }

            // Edit Button (overlapping top-right)
            IconButton(
                onClick = { showEditDialog = true },
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Contact",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))
        // Name
        Text(
            text = "Ridwan Muhamad Ramdani",
            fontFamily = JakartaSans,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "ridwan aja",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontFamily = JakartaSans,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )

    }


    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            confirmButton = {
                Button(onClick = { showEditDialog = false }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Contact") },

            )
    }
}
@Composable
fun ProfileContentItems(
    onMembershipClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit
) {
    val profileItems = listOf(
        ProfileItem(
            icon = Icons.Default.WorkspacePremium,
            text = stringResource(R.string.membership),
            onClick = onMembershipClick
        ),
        ProfileItem(
            icon = Icons.Default.PhoneInTalk,
            text = stringResource(R.string.contact_us),
            onClick = onContactUsClick
        ),
        ProfileItem(
            icon = Icons.Default.LocalPolice,
            text = stringResource(R.string.privacy_policy),
            onClick = onPrivacyPolicyClick
        ),
        ProfileItem(
            icon = Icons.Default.ContentPaste,
            text = stringResource(R.string.term_and_condition),
            onClick = onTermsClick
        )
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        profileItems.forEach { item ->
            ProfileContentRow(
                icon = item.icon,
                text = item.text,
                onClick = item.onClick
            )
            HorizontalDivider(modifier = Modifier.height(2.dp))
        }
    }
}

data class ProfileItem(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit
)

@Composable
private fun ProfileContentRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



@Composable
fun LogoutContentRow(onLogout: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable(enabled = onLogout != null) { onLogout?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = null,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.logout),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

