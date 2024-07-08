package com.innoprog.android.feature.training.courseInformation.data.network

import com.google.gson.annotations.SerializedName

data class CourseInformationDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("direction")
    val direction: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("author")
    val author: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("usefulLinks")
    val usefulLinks: String?,
    @SerializedName("attachments")
    val attachments: List<AttachmentsDto>
)

class AttachmentsDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)