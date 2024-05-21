package com.innoprog.android.feature.feed.projectScreen.domain

data class AnyProjectModel(
    val id: String,
    val name: String,
    val shortDescription: String,
    val logoFilePath: String,
    val publicationsCount: Int,
    val area: String
)
