package com.example.data.response

import com.google.gson.annotations.SerializedName

data class ProjectDetailResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: DataProject
)

data class DataProject(
    @SerializedName("project") val project: Project,
    @SerializedName("developer") val developer: List<Developer>,
    @SerializedName("contractor") val contractor: List<Contractor>,
    @SerializedName("consultant") val consultant: List<Consultant>,
    @SerializedName("project_specification") val projectSpecification: List<ProjectSpecification>,
    @SerializedName("project_ppr") val projectPpr: List<ProjectPpr>,
    @SerializedName("show_cp") val showCp: Boolean,
    @SerializedName("show_cp_email") val showCpEmail: Boolean,
    @SerializedName("show_cp_phone") val showCpPhone: Boolean,
    @SerializedName("project_update_status") val projectUpdateStatus: List<ProjectUpdateStatus>,
    @SerializedName("member_comment") val memberComment: List<Any>
)

data class Project(
    @SerializedName("idproject") val idProject: String,
    @SerializedName("iddeveloper") val idDeveloper: String?,
    @SerializedName("idproject_specification") val idProjectSpecification: String?,
    @SerializedName("idprovince") val idProvince: String,
    @SerializedName("idcity") val idCity: String,
    @SerializedName("idproject_category") val idProjectCategory: String,
    @SerializedName("idproject_status_category") val idProjectStatusCategory: String,
    @SerializedName("idbuilding_category") val idBuildingCategory: String,
    @SerializedName("date") val date: String?,
    @SerializedName("note") val note: String,
    @SerializedName("status_record") val statusRecord: String,
    @SerializedName("update_status") val updateStatus: String,
    @SerializedName("publish_record") val publishRecord: String,
    @SerializedName("show_record") val showRecord: String?,
    @SerializedName("expired_date") val expiredDate: String,
    @SerializedName("project_code") val projectCode: String,
    @SerializedName("project_name") val projectName: String,
    @SerializedName("location") val location: String,
    @SerializedName("source_fund") val sourceFund: String,
    @SerializedName("construction_schedule") val constructionSchedule: String,
    @SerializedName("month_year_start") val monthYearStart: String,
    @SerializedName("month_year_end") val monthYearEnd: String,
    @SerializedName("stage") val stage: String,
    @SerializedName("project_status") val projectStatus: String?,
    @SerializedName("budget") val budget: String,
    @SerializedName("path") val path: String?,
    @SerializedName("path2") val path2: String?,
    @SerializedName("path3") val path3: String?,
    @SerializedName("created") val created: String,
    @SerializedName("createdby") val createdBy: String,
    @SerializedName("updated") val updated: String,
    @SerializedName("updatedby") val updatedBy: String,
    @SerializedName("status") val status: String,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("description") val description: String,
    @SerializedName("province_name") val provinceName: String,
    @SerializedName("city_name") val cityName: String,
    @SerializedName("sequence") val sequence: String,
    @SerializedName("project_status_category_name") val projectStatusCategoryName: String,
    @SerializedName("building_category_name") val buildingCategoryName: String,
    @SerializedName("project_created") val projectCreated: String
)

data class Developer(
    @SerializedName("name") val name: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("address") val address: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("fax") val fax: String,
    @SerializedName("email") val email: String,
    @SerializedName("website") val website: String,
    @SerializedName("team") val team: List<TeamMember>,
    @SerializedName("note") val note: String
)

data class Contractor(
    @SerializedName("name") val name: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("address") val address: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("fax") val fax: String,
    @SerializedName("email") val email: String,
    @SerializedName("website") val website: String,
    @SerializedName("team") val team: List<TeamMember>,
    @SerializedName("note") val note: String
)

data class Consultant(
    @SerializedName("name") val name: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("address") val address: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("fax") val fax: String,
    @SerializedName("email") val email: String,
    @SerializedName("website") val website: String,
    @SerializedName("team") val team: List<TeamMember>,
    @SerializedName("note") val note: String
)

data class TeamMember(
    @SerializedName("structure_name") val structureName: String,
    @SerializedName("position") val position: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
)

data class ProjectSpecification(
    @SerializedName("idproject_specification") val idProjectSpecification: String,
    @SerializedName("idproject") val idProject: String,
    @SerializedName("key") val key: String,
    @SerializedName("value") val value: String,
    @SerializedName("created") val created: String,
    @SerializedName("createdby") val createdBy: String,
    @SerializedName("updated") val updated: String,
    @SerializedName("updatedby") val updatedBy: String,
    @SerializedName("status") val status: String
)

data class ProjectUpdateStatus(
    @SerializedName("update_status") val updateStatus: String,
    @SerializedName("date") val date: String
)

data class ProjectPpr(
    @SerializedName("id") val id: String,
    @SerializedName("ppr_code") val pprCode: String,
    @SerializedName("category_name") val categoryName: String,
    @SerializedName("ppr_created") val pprCreated: String,
    @SerializedName("location") val location: String,
    @SerializedName("tender_month") val tenderMonth: String,
    @SerializedName("tender_package") val tenderPackage: String,
    @SerializedName("tender_participant") val tenderParticipant: String,
    @SerializedName("tender_notice") val tenderNotice: String,
    @SerializedName("new_info") val newInfo: String,
    @SerializedName("contact_info_business") val contactInfoBusiness: String?,
    @SerializedName("ppr_developer") val pprDeveloper: List<Developer>,
    @SerializedName("ppr_consultant") val pprConsultant: List<Consultant>,
    @SerializedName("ppr_contractor") val pprContractor: List<Contractor>
)
