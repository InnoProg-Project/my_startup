package com.innoprog.android.feature.edit.data.dto

import com.innoprog.android.feature.edit.domain.model.ProjectModel


fun ProjectResponse.convert(): ProjectModel {
    return ProjectModel(
        id = id,
        name = name,
        area = area,
        logoUrl = logoFilePath
    )
}
