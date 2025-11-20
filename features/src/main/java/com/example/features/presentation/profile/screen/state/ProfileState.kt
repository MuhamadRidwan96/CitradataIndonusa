package com.example.features.presentation.profile.screen.state

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileState(
    val statusInfo : StatusInfo  = StatusInfo(),
    val basicInfo: BasicInfo = BasicInfo(),
    val contactInfo: ContactInfo = ContactInfo(),
    val professionalInfo: ProfessionalInfo = ProfessionalInfo(),
    val accountInfo: AccountInfo = AccountInfo(),
    val subscriptionInfo: SubscriptionInfo = SubscriptionInfo(),
    val metadata: Metadata = Metadata()
) {

    @Immutable
    data class StatusInfo(
        val isLoading: Boolean = true,
        val error: String? = null
    )

    @Immutable
    data class BasicInfo(
        val name: String = "",
        val fullName: String = "",
        val photo: String = "",
        val username: String = ""
    )

    @Immutable
    data class ContactInfo(
        val email: String = "",
        val phone: String? = null,
        val address: String = "",
        val website: String = ""
    )

    @Immutable
    data class ProfessionalInfo(
        val position: String = "",
        val company: String = "",
        val note: String = ""
    )

    @Immutable
    data class AccountInfo(
        val idUser: String = "",
        val idUserMaster: String? = null,
        val idRole: String = "",
        val roleName: String = "",
        val userStatus: String = "",
        val userType: String = ""
    )

    @Immutable
    data class SubscriptionInfo(
        val packageMemberType: String = "",
        val subscriptionFee: String = "",
        val totalFee: String = "",
        val counted: String = "",
        val startDate: String = "",
        val endDate: String = ""
    )

    @Immutable
    data class Metadata(
        val idProvince: String? = null,
        val idCity: String? = null,
        val created: String = "",
        val createdBy: String = "",
        val updated: String = "",
        val updatedBy: String = "",
        val status: String = ""
    )
}