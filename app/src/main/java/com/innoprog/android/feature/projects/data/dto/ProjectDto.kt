package com.innoprog.android.feature.projects.data.dto

import java.util.Date
import java.util.UUID

data class ProjectDto(
    val id: UUID,
    val name: String,
    val shortDescription: String,
    val description: String,
    val logoFilePath: String,
    val publicationsCount: Int,
    val area: String,
    val financingStage: String,
    val deadline: Date,
    val siteUrls: String,
    val documentUrls: List<String>,
    val projectAttachments: List<ProjectAttachmentDto>
)