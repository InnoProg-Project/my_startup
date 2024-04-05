package com.innoprog.android.feature.edit.domain.model

data class PublicationModel(
    val projectId: String,
    val title: String,
    val content: String,
    val attachments: List<String>
)
