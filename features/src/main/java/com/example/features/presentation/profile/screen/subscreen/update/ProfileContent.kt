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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.features.presentation.profile.screen.state.CardInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ContentProfileScreen(
    modifier: Modifier = Modifier,

    state: CardInfo,

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

                    cardInfo = CardInfo(
                        name = state.name,
                        fullName = state.fullName,
                        email = state.email,
                        address = state.address,
                        company = state.company,
                        phone = state.phone,
                        dateEnd = state.dateEnd
                    )
                )
            }

            item(key = "profileContent") {
                ProfileItemList(
                    items = mainItems
                )
            }
        }
    }
}

@Composable
fun ProfileItemList(modifier: Modifier = Modifier, items: ImmutableList<ProfileItem>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items.forEachIndexed { index, item ->
            ProfileItemRow(

                item = item
            )
            if (index < items.lastIndex) {
                ProfileDivider()
            }
        }
    }
}


@Composable
fun CardProfile(
    modifier: Modifier = Modifier,
    cardInfo: CardInfo
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
                text = cardInfo.name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = cardInfo.fullName,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )

            ProfileText(
                email = cardInfo.email,
                address = cardInfo.address,
                company = cardInfo.company,
                phone = cardInfo.phone,
                dateEnd = cardInfo.dateEnd
            )

        }
    }
}

@Composable
fun ProfileText(
    modifier: Modifier = Modifier,
    email: String,
    address: String,
    company: String,
    phone: String?,
    dateEnd: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
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
fun ImageProfile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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


