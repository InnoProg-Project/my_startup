package com.innoprog.android.feature.edit.domain.useCase

import com.innoprog.android.feature.edit.domain.model.PublicationModel
import kotlinx.coroutines.flow.Flow

interface EditePublishUseCase {
    suspend fun updatePublication(publicationModel: PublicationModel)

    fun getPublicationById(id: String): Flow<PublicationModel>
}
