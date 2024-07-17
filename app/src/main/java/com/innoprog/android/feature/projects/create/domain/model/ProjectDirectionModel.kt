package com.innoprog.android.feature.projects.create.domain.model

@Suppress("Detekt.DataClassShouldBeImmutable")
data class ProjectDirectionModel(
    val title: String,
    var isSelected: Boolean = false,
)
