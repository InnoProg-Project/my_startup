package com.innoprog.android.feature.edit.data

import com.innoprog.android.feature.edit.domain.model.IdeaModel
import com.innoprog.android.feature.edit.domain.model.ProjectModel
import com.innoprog.android.feature.edit.domain.model.PublicationModel
import kotlinx.coroutines.flow.Flow

interface CreateEditContentRepository {
    suspend fun createIdea(ideaModel: IdeaModel)

    suspend fun createPublication(publicationModel: PublicationModel)

    fun getProjectById(id: String): Flow<ProjectModel>

    suspend fun updatePublication(publicationModel: PublicationModel)


    fun getPublicationById(id: String): Flow<PublicationModel>
}