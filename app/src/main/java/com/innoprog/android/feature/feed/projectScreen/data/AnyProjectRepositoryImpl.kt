package com.innoprog.android.feature.feed.projectScreen.data

import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectModel
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectRepository
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Suppress("Detekt.StringLiteralDuplication")
class AnyProjectRepositoryImpl @Inject constructor() : AnyProjectRepository {

    val company = Company(
        "HighTechCorp",
        "CEO"
    )

    val author = Author(
        "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "Юлия Анисимова",
        company
    )

    val news = News(
        id = "1",
        type = "idea",
        author = author,
        projectId = "1",
        coverUrl = "",
        title = "Как мы помогаем родителям в воспитании детей ",
        content = "Этот надежный помощник предназначен для облегчения путей родительства и " +
            "обеспечения гармоничного развития маленьких личностей",
        publishedAt = "24 февраля 2024 в 12:43",
        likesCount = 24,
        commentsCount = 24,
    )

    val news2 = News(
        id = "2",
        type = "project",
        author = author,
        projectId = "2",
        coverUrl =
        "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
            "vector-digital-transformation-concept_53876-112222.jpg",
        title = "Искусственный интеллект",
        content = "Иску́сственный интелле́кт — свойство искусственных интеллектуальных систем " +
            "выполнять творческие функции, которые традиционно считаются прерогативой " +
            "человека (не следует путать с искусственным сознанием)",
        publishedAt = "24 февраля 2024 в 12:43",
        likesCount = 24,
        commentsCount = 24,
    )

    private val projectInFeed = Project(
        id = "1",
        name = "Мой стартап",
        area = "Искусственный интеллект",
        logoUrl = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
            "vector-digital-transformation-concept_53876-112222.jpg",
    )

    private val newsWithProject1 = NewsWithProject(
        news = news,
        project = null
    )

    private val newsWithProject2 = NewsWithProject(
        news = news2,
        project = projectInFeed
    )

    private val project = AnyProjectModel(
        id = "1",
        name = "Мой стартап",
        shortDescription = "Проект для помощи генерации идей и изучения проектов",
        logoFilePath = "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
            "vector-digital-transformation-concept_53876-112222.jpg",
        publicationsCount = 24,
        area = "Искусственный интеллект",
        arrayListOf(newsWithProject1, newsWithProject2, newsWithProject1, newsWithProject2)
    )

    override fun getAnyProject(id: String): Flow<Resource<AnyProjectModel>> = flow {
        emit(Resource.Success(project))
    }
}
