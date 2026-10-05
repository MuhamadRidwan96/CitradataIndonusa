package com.example.domain.model.subscription

import com.example.domain.model.project.BuildingCategoryEntitlement
import com.example.domain.model.project.ProjectCategoryEntitlement
import com.example.domain.model.project.ProjectStatusEntitlement
import com.example.domain.model.project.ProvinceEntitlement
import com.google.gson.annotations.SerializedName

data class SubscriptionEntitlements(

    @SerializedName("plan")
    val plan: EntitlementPlan,

    @SerializedName("features")
    val features: List<SubscriptionFeature>,

    @SerializedName("permissions")
    val permissions: List<SubscriptionPermission>,

    @SerializedName("project_category")
    val projectCategories: List<ProjectCategoryEntitlement>,

    @SerializedName("building_category")
    val buildingCategories: List<BuildingCategoryEntitlement>,

    @SerializedName("province")
    val provinces: List<ProvinceEntitlement>,

    @SerializedName("project_status")
    val projectStatuses: List<ProjectStatusEntitlement>
)
