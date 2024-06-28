package com.innoprog.android.feature.projects.chooseProjectDirection.domain.model

@Suppress("Detekt.DataClassShouldBeImmutable")
data class ProjectDirectionModel(
    val title: String,
    var isSelected: Boolean = false,
)
