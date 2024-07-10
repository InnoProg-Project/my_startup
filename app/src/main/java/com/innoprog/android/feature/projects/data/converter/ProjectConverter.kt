package com.innoprog.android.feature.projects.data.converter

import com.innoprog.android.feature.projects.data.dto.ProjectAttachmentDto
import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.feature.projects.domain.models.ProjectAttachment

fun ProjectDto.convert() = Project(
    id,
    name,
    shortDescription,
    description,
    logoFilePath,
    publicationsCount,
    area,
    financingStage,
    deadline,
    siteUrls,
    documentUrls,
    projectAttachments.map { it.convert() }
)

fun ProjectAttachmentDto.convert() = ProjectAttachment(
    id,
    filePath,
    type
)