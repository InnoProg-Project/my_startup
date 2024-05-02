package com.innoprog.android.feature.edit.domain.useCase.impl

import com.innoprog.android.feature.edit.data.impl.CreateEditContentRepositoryImpl
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import com.innoprog.android.feature.edit.domain.useCase.EditePublishUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditePublishUseCaseImpl @Inject constructor(
    private val repository: CreateEditContentRepositoryImpl
) : EditePublishUseCase {
    override suspend fun updatePublication(publicationModel: PublicationModel) {
        repository.updatePublication(publicationModel)
    }

    override fun getPublicationById(id: String): Flow<PublicationModel> {
        return repository.getPublicationById(id)
    }
}