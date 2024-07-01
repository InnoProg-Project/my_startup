package com.innoprog.android.feature.edit.data.dto

import com.innoprog.android.feature.edit.domain.model.ProjectModel
import javax.inject.Inject

class EditContentAdapter @Inject constructor() {
    fun mapToProjectModel(projectResponse: ProjectResponse): ProjectModel {
        return ProjectModel(
            id = projectResponse.id,
            name = projectResponse.name,
            area = projectResponse.area,
            logoUrl = projectResponse.logoFilePath
        )
    }
}