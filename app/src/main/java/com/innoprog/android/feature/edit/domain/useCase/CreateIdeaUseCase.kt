package com.innoprog.android.feature.edit.domain.useCase

import com.innoprog.android.feature.edit.domain.model.IdeaModel

interface CreateIdeaUseCase {

    suspend fun createIdea(ideaModel: IdeaModel)
}
