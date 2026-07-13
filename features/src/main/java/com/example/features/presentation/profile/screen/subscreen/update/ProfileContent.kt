package com.example.features.presentation.profile.screen.subscreen.update

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.features.presentation.profile.screen.state.CardInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList


@Composable
fun ContentProfileScreen(
    modifier: Modifier = Modifier,
    state: CardInfo,
    onEditClick : () -> Unit,
    onMembershipClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onLogout: (() -> Unit)? = null,


    ) {

    val mainItems = remember {
        (persistentListOf(
            ProfileItem.Regular(
                R.drawable.award,
                R.string.membership,
                onMembershipClick
            ),
            ProfileItem.Regular(
                R.drawable.phone,
                R.string.call_us,
                onContactUsClick
            ),
            ProfileItem.Regular(
                R.drawable.shield_user,
                R.string.privacy_policy,
                onPrivacyPolicyClick
            ),
            ProfileItem.Regular(
                R.drawable.notebook_text,
                R.string.term_and_condition,

                onTermsClick
            )
        ) + if (onLogout != null) {
            listOf(ProfileItem.Logout(onLogout, true))
        } else emptyList()).toPersistentList()
    }


    // Scrollable content
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {

        item {
            ProfileHeader(
                cardInfo = state,
                onEditProfile = onEditClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            CardProfile(
                modifier = Modifier.padding(horizontal = 16.dp),
                cardInfo = state
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ProfileItemList(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = mainItems
            )
        }

    }
}

@Composable
fun ProfileItemList(modifier: Modifier = Modifier, items: ImmutableList<ProfileItem>) {

    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            items.forEachIndexed { index, item ->
                ProfileItemRow(
                    item = item,
                )
                if (index < items.lastIndex) {
                    ProfileDivider()
                }
            }
        }
    }
}


@Composable
fun CardProfile(
    modifier: Modifier = Modifier,
    cardInfo: CardInfo
) {

    ElevatedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp))
        {

            ProfileInfoRow(
                "Nama Lengkap",
                cardInfo.fullName
            )

            ProfileInfoRow(
                "Email",
                cardInfo.email
            )

            ProfileInfoRow(
                "Alamat",
                cardInfo.address
            )

            ProfileInfoRow(
                "Nomor Telepon",
                cardInfo.phone ?: "-"
            )

            ProfileInfoRow(
                "Perusahaan",
                cardInfo.company
            )

            ProfileInfoRow(
                "Masa Berlaku",
                cardInfo.dateEnd,
                showDivider = false
            )
        }
    }

}





@Composable
fun ProfileHeader(modifier: Modifier = Modifier, cardInfo: CardInfo, onEditProfile: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageProfile()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = cardInfo.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = cardInfo.company,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilledTonalButton(
            onClick = onEditProfile
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Edit Profile")
        }


    }
}

@Composable
fun ImageProfile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(123.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center

    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}


//item information
@Composable
fun ProfileInfoRow(
    title: String,
    value: String, modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )

        if (showDivider) {

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}


@Composable
fun ProfileItemRow(
    modifier: Modifier = Modifier,
    item: ProfileItem

) {
    val titleText = when (item) {
        is ProfileItem.Regular -> stringResource(item.title)
        is ProfileItem.Logout -> stringResource(item.title)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = item.enabled,
                onClick = item.onClick
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(item.icon),
            modifier = Modifier.size(18.dp),
            contentDescription = null,
            colorFilter = when (item) {
                is ProfileItem.Logout -> ColorFilter.tint(Color.Red)
                else -> ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            }
        )

        Text(
            text = titleText,
            color = when (item) {
                is ProfileItem.Logout -> Color.Gray
                else -> MaterialTheme.colorScheme.onSurface
            },
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.weight(1f))

        if (item !is ProfileItem.Logout) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun ProfileDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 0.9.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Immutable
sealed interface ProfileItem {
    val icon: Int
    val onClick: () -> Unit
    val enabled: Boolean
        get() = true

    data class Regular(
        @DrawableRes override val icon: Int,
        @StringRes val title: Int,
        override val onClick: () -> Unit
    ) : ProfileItem

    data class Logout(
        override val onClick: () -> Unit,
        override val enabled: Boolean
    ) : ProfileItem {
        @DrawableRes
        override val icon: Int = R.drawable.log_out
        val title: Int = R.string.logout
    }
}


