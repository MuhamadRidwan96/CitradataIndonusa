package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class FilterDataModel(
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("idproject")
    val idProject: String,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("idproject_category")
    val idProjectCategory: String,
    @SerializedName("idbuilding_category")
    val idBuildingCategory: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("idprovince")
    val idProvince: String,
    @SerializedName("idcity")
    val idCity: String,
    @SerializedName("iddeveloper")
    val idDeveloper: String,
    @SerializedName("idconsultant")
    val idConsultant: String,
    @SerializedName("idconsultant_category")
    val idConsultantCategory: String,
    @SerializedName("idcontractor")
    val idContractor: String,
    @SerializedName("idcontractor_category")
    val idContractorCategory: String,
    @SerializedName("idproject_status_category")
    val idProjectStatusCategory: String,
    @SerializedName("with_ppr")
    val withPpr: String
)