package com.innoprog.android.feature.edit.domain.model

import android.net.Uri

data class PublicationModel(
    val projectId: String,
    val title: String,
    val content: String,
    val mediaAttachments: List<Uri>
)
