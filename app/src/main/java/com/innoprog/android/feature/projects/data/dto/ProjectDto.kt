package com.innoprog.android.feature.projects.data.dto

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID

data class ProjectDto(
    @SerializedName("id")
    val id: UUID,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("logoFilePath")
    val logoFilePath: String,
    @SerializedName("publicationsCount")
    val publicationsCount: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("financingStage")
    val financingStage: String,
    @SerializedName("deadline")
    val deadline: Date,
    @SerializedName("siteUrls")
    val siteUrls: String,
    @SerializedName("documentUrls")
    val documentUrls: List<String>,
    @SerializedName("projectAttachments")
    val projectAttachments: List<ProjectAttachmentDto>
)