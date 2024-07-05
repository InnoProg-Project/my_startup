package com.innoprog.android.feature.projects.domain.models

import java.util.UUID

data class ProjectAttachment(
    val id: UUID,
    val filePath: String,
    val type: String
)