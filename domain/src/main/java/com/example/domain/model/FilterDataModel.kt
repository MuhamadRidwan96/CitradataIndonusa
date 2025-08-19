package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class FilterDataModel(
    @SerializedName("start_date")
    val startDate: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    @SerializedName("idproject")
    val idProject: String? = null,
    @SerializedName("project_name")
    val projectName: String? = null,
    @SerializedName("idproject_category")
    val idProjectCategory: String? = null,
    @SerializedName("idbuilding_category")
    val idBuildingCategory: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("idprovince")
    val idProvince: String? = null,
    @SerializedName("idcity")
    val idCity: String? = null,
    @SerializedName("iddeveloper")
    val idDeveloper: String? = null,
    @SerializedName("idconsultant")
    val idConsultant: String? = null,
    @SerializedName("idconsultant_category")
    val idConsultantCategory: String? = null,
    @SerializedName("idcontractor")
    val idContractor: String? = null,
    @SerializedName("idcontractor_category")
    val idContractorCategory: String? = null,
    @SerializedName("idsector_category")
    val idSectorCategory: String? = null,
    @SerializedName("idproject_status_category")
    val idProjectStatusCategory: String? = null,
    @SerializedName("with_ppr")
    val withPpr: String? = null,
    @SerializedName("province")
    val province : String? = null
){
    companion object
}