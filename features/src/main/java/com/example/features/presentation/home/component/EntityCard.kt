package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.domain.response.TeamMember
import kotlinx.collections.immutable.persistentListOf

@Composable
fun EntityCard(
    @DrawableRes icon: Int,
    section: String,
    name: String,
    address: String,
    phone: String,
    email: String,
    web: String,
    fax: String,
    note: String,
    teamMembers: List<TeamMember>,
    modifier: Modifier = Modifier

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            TitleSection(icon, section)

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            IconText(R.drawable.ic_phone, phone)
            IconText(R.drawable.ic_mail, email)
            IconText(R.drawable.link_24px, web)
            IconText(R.drawable.ic_fax, fax)


            Spacer(Modifier.height(8.dp))

            if (teamMembers.isNotEmpty()) {

                Text(
                    text = stringResource(R.string.team_member),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color.Gray
                )
                Spacer(Modifier.height(8.dp))

                teamMembers.forEach { member ->
                    TeamMemberComponent(member)
                    Spacer(Modifier.height(4.dp))

                }
            }
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun TeamMemberComponent(teamMember: TeamMember) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        Text(
            text = teamMember.structureName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        if (teamMember.position.isNotBlank()) {
            Text(
                text = teamMember.position,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        if (teamMember.phone.isNotBlank()) {
            IconText(R.drawable.ic_phone, teamMember.phone)
        }
        if (teamMember.email.isNotBlank()) {
            IconText(R.drawable.ic_mail, teamMember.email)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEntityCard() {

    // Dummy Team Members
    val dummyTeamMembers = persistentListOf(
        TeamMember(
            structureName = "Ridwan",
            position = "Manager",
            phone = "082132810",
            email = "m.rmdn.96@gmail.com"
        ),
        TeamMember(
            structureName = "Muhamad",
            position = "Supervisor",
            phone = "092138324923",
            email = "adakjdbaksjb@gmail.com"
        )
    )

    EntityCard(
        icon = R.drawable.ic_mail, // ganti dengan drawable kamu
        section = "Developer",
        name = "PT Contoh Konstruksi Indonesia",
        address = "Jl. Raya Sudirman No. 123, Jakarta Pusat",
        phone = "021-1234567",
        email = "info@contoh.co.id",
        web = "www.contoh.co.id",
        fax = "021-7654321",
        note = "Perusahaan konstruksi terkemuka di Indonesia.",
        teamMembers = dummyTeamMembers,
        modifier = Modifier.padding(16.dp)
    )
}
