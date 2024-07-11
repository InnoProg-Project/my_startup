package com.innoprog.android.feature.projects.create.domain.model

import java.util.UUID

data class ProjectAttachment(
    val id: UUID,
    val filePath: String,
    val type: String
)