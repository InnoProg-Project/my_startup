package com.innoprog.android.feature.edit.data.impl

import com.innoprog.android.feature.edit.data.CreateEditContentRepository
import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateEditContentRepositoryImpl @Inject constructor() : CreateEditContentRepository {
    override suspend fun createIdea(ideaModel: IdeaModel) {

    }

    override suspend fun createPublication(publicationModel: PublicationModel) {

    }

    override fun getProjectById(id: String): Flow<ProjectModel> {
        return flow { }
    }

    override suspend fun updatePublication(publicationModel: PublicationModel) {

    }

    override fun getPublicationById(id: String): Flow<PublicationModel> {
        return flow { }
    }
}