package com.innoprog.android.feature.projects.create.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ProjectBody(
    @SerializedName("data")
    val data: ProjectInformation,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("attachments")
    val attachments: List<String>
)

data class ProjectInformation(
    @SerializedName("attachments")
    val name: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("financingStage")
    val financingStage: String,
    @SerializedName("siteUrls")
    val siteUrls: String,
    @SerializedName("documentUrls")
    val documentUrls: List<String>,
    @SerializedName("deadline")
    val deadline: LocalDate
)
