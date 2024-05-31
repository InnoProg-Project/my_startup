package com.innoprog.android.feature.edit.domain.useCase.impl

import com.innoprog.android.feature.edit.data.impl.CreateEditContentRepositoryImpl
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.MediaAttachmentsModel
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import com.innoprog.android.util.Resource
import javax.inject.Inject

class CreateIdeaUseCaseImpl @Inject constructor(
    private val repository: CreateEditContentRepositoryImpl
) :
    CreateIdeaUseCase {
    override suspend fun createIdea(ideaModel: IdeaModel) {
        repository.createIdea(ideaModel)
    }

    override suspend fun addMediaToListAttachments(path: String): Resource<MediaAttachmentsModel> {
        return repository.addMediaToListAttachments(path)
    }

    override suspend fun deleteMediaFromListAttachments(path: String): Resource<MediaAttachmentsModel> {
        return repository.deleteMediaFromListAttachments(path)
    }

    override suspend fun getMediaAttachments(): Resource<MediaAttachmentsModel> {
        return repository.getMediaAttachments()
    }

}
