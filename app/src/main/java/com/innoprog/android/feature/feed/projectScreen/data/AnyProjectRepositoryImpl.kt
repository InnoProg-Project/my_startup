package com.innoprog.android.feature.feed.projectScreen.data

import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectModel
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectRepository
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnyProjectRepositoryImpl @Inject constructor() : AnyProjectRepository {

    private val project = AnyProjectModel(
        id = "1",
        name = "Мой стартап",
        shortDescription = "Проект для помощи генерации идей и изучения проектов",
        logoFilePath = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
            "vector-digital-transformation-concept_53876-112222.jpg",
        publicationsCount = 24,
        area = "Искусственный интеллект"
    )

    override fun getAnyProject(id: String): Flow<Resource<AnyProjectModel>> = flow {
        emit(Resource.Success(project))
    }
}
