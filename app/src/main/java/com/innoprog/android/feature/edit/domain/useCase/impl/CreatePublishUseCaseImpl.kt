package com.innoprog.android.feature.edit.domain.useCase.impl

import com.innoprog.android.feature.edit.data.CreateEditContentRepository
import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import com.innoprog.android.feature.edit.domain.useCase.CreatePublishUseCase
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePublishUseCaseImpl @Inject constructor(
    private val createEditContentRepository: CreateEditContentRepository
) : CreatePublishUseCase {

    override suspend fun createPublication(publicationModel: PublicationModel) {
        createEditContentRepository.createPublication(publicationModel)
    }

    override fun getProjectById(id: String): Flow<Resource<ProjectModel>> {
        return createEditContentRepository.getProjectById(id)
    }
}