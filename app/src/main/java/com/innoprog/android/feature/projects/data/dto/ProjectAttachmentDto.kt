package com.innoprog.android.feature.projects.data.dto

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ProjectAttachmentDto(
    @SerializedName("id")
    val id: UUID,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)