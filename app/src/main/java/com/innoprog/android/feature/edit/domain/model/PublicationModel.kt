package com.innoprog.android.feature.edit.domain.model

data class PublicationModel(
    val publicationId: String? = null,
    val projectId: String,
    val title: String,
    val content: String,
)
