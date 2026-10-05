package com.example.domain.model.project

import com.google.gson.annotations.SerializedName

data class ProjectCategoryEntitlement(

    @SerializedName("idproject_category")
    val id: Int,

    @SerializedName("category_name")
    val categoryName: String
)

data class BuildingCategoryEntitlement(

    @SerializedName("idbuilding_category")
    val id: Int,

    @SerializedName("category_name")
    val categoryName: String
)

data class ProvinceEntitlement(

    @SerializedName("idprovince")
    val id: Int,

    @SerializedName("province_name")
    val provinceName: String
)

data class ProjectStatusEntitlement(

    @SerializedName("idproject_status_category")
    val id: Int,

    @SerializedName("category_name")
    val categoryName: String
)

