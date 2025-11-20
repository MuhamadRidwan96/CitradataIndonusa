package com.example.features.presentation.profile.screen.subscreen.update

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText

@Composable
fun ContentProfileScreen(
    modifier: Modifier = Modifier,
    onMembershipClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onLogout: (() -> Unit)? = null,
    name: String,
    fullName: String,
    email: String,
    address: String,
    company: String,
    phone: String?,
    dateEnd: String

) {

    val items = remember {
        listOf(
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
        } else emptyList()

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Background header
        Box(
            modifier = Modifier
                .height(125.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomEnd = 44.dp)
                )
        )

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item(key = "l") {
                Spacer(modifier = Modifier.height(35.dp))
                CardProfile(
                    name = name,
                    fullName = fullName,
                    email = email,
                    address = address,
                    company = company,
                    phone = phone,
                    dateEnd = dateEnd
                )
            }

            item(key = "profileContent") {
                ProfileItemList(items)
            }
        }
    }
}

@Composable
fun ProfileItemList(items: List<ProfileItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items.forEachIndexed { index, item ->
            ProfileItemRow(item)
            if (index < items.lastIndex) {
                ProfileDivider()
            }
        }
    }
}


@Composable
fun CardProfile(
    modifier: Modifier = Modifier,
    name: String,
    fullName: String,
    email: String,
    address: String,
    company: String,
    phone: String?,
    dateEnd: String
) {

    Card(
        modifier = modifier.padding(24.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageProfile()
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = fullName,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )

            ProfileText(
                email = email,
                address = address,
                company = company,
                phone = phone,
                dateEnd = dateEnd
            )

        }
    }
}

@Composable
fun ProfileText(
    email: String,
    address: String,
    company: String,
    phone: String?,
    dateEnd: String
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconText(R.drawable.mail, email)
        IconText(R.drawable.phone, phone ?: "")
        IconText(R.drawable.map_pin_house, address)
        IconText(R.drawable.factory, company)
        IconText(R.drawable.calendar, dateEnd)
    }
}

@Composable
fun ImageProfile() {
    Box(
        modifier = Modifier
            .size(94.dp),
        contentAlignment = Alignment.Center
    ) {
        // Profile Image
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}


@Composable
fun ProfileItemRow(
    item: ProfileItem,
    modifier: Modifier = Modifier
) {
    val titleText = when (item) {
        is ProfileItem.Regular -> stringResource(item.title)
        is ProfileItem.Logout -> stringResource(item.title)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(
                enabled = item.enabled,
                onClick = item.onClick
            ),
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

@Stable
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


