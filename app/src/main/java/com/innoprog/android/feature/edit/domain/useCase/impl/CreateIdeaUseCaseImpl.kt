package com.innoprog.android.feature.edit.domain.useCase.impl

import com.innoprog.android.feature.edit.data.impl.CreateEditContentRepositoryImpl
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.useCase.CreateIdeaUseCase
import javax.inject.Inject

class CreateIdeaUseCaseImpl @Inject constructor(
    private val repository: CreateEditContentRepositoryImpl
) :
    CreateIdeaUseCase {
    override suspend fun createIdea(ideaModel: IdeaModel) {
        repository.createIdea(ideaModel)
    }
}
