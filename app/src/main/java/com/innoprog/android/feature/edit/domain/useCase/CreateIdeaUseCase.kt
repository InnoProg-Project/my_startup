package com.innoprog.android.feature.edit.domain.useCase

import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.MediaAttachmentsModel
import com.innoprog.android.util.Resource

interface CreateIdeaUseCase {

    suspend fun createIdea(ideaModel: IdeaModel)

    suspend fun addMediaToListAttachments(path: String): Resource<MediaAttachmentsModel>

    suspend fun deleteMediaFromListAttachments(path: String): Resource<MediaAttachmentsModel>

    suspend fun getMediaAttachments(): Resource<MediaAttachmentsModel>

}
