package com.innoprog.android.feature.edit.domain.model

import android.net.Uri

data class IdeaModel(
    val title: String,
    val content: String,
    val mediaAttachments: List<Uri>
)
