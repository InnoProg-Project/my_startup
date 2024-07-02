package com.innoprog.android.feature.projects.data.dto

import java.util.UUID

data class ProjectAttachmentDto(
    val id: UUID,
    val filePath: String,
    val type: String
)