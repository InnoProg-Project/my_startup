package com.innoprog.android.feature.edit.domain.useCase

import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import kotlinx.coroutines.flow.Flow

interface CreatePublishUseCase {
    suspend fun createPublication(publicationModel: PublicationModel)

    fun getProjectById(id: String): Flow<ProjectModel>
}
