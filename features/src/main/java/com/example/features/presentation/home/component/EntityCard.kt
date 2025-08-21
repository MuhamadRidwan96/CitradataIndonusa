import androidx.annotation.DrawableRes
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
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.domain.response.TeamMember
import com.example.features.presentation.home.component.TitleSection
import kotlin.collections.forEach

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {

            TitleSection(icon, section)
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            IconText(R.drawable.ic_phone, phone)
            IconText(R.drawable.ic_mail, email)
            IconText(R.drawable.ic_web, web)
            IconText(R.drawable.ic_fax, fax)

            if (teamMembers.isNotEmpty()) {
                Spacer(modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.team_member),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier.height(4.dp))
                teamMembers.forEach { member ->
                    TeamMemberComponent(member)
                    Spacer(modifier.height(8.dp))
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
    Text(
        text = teamMember.structureName,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.fillMaxWidth()
    )
    if (teamMember.position.isNotBlank()) {
        Text(
            text = teamMember.position,
            style = MaterialTheme.typography.bodySmall,
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