package com.innoprog.android.feature.feed.newsdetails.data

import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsDetailsRepositoryImpl @Inject constructor() : NewsDetailsRepository {

    private val company = Company(
        "HighTechCorp",
        "CEO"
    )

    private val author = Author(
        "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "https://img.freepik.com/free-vector/ai-technology-microchip-background-" +
            "vector-digital-transformation-concept_53876-112222.jpg",
        "Юлия Анисимова",
        company
    )

    private val comment = CommentModel(
        "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "Мария Иванова",
        "Перспективный проект!",
        "2024-05-07T13:27:13.023Z"
    )

    private val newsDetails = NewsDetailsModel(
        id = "1",
        type = "idea",
        author = author,
        projectId = "1",
        coverUrl = "",
        title = "Помощник в воспитании детей",
        content = "Образовательный уровень родителей зачастую влияет на будущее детей больше, " +
            "чем их успеваемость в школе. Сотрудники Института образования НИУ ВШЭ выяснили, " +
            "какую роль играет этот фактор в том выборе, который школьник делает после 9 и 11 " +
            "классов. Как следует из результатов исследования, далеко не всегда этот выбор " +
            "продиктован академической успеваемостью. Семья с высоким уровнем образования  " +
            "помогает школьнику выбрать такие стратегии, которые позволяют получить хорошее " +
            "образование даже при невысокой успеваемости. И наоборот: дети из семей с невысоким " +
            "образовательным уровнем чаще готовы согласиться на меньшее или отказаться от тех " +
            "возможностей, которые открывают им текущие образовательные результаты.",
        publishedAt = "2024-05-07T13:27:13.023Z",
        likesCount = 24,
        commentsCount = 24,
        isLiked = false,
        isFavorite = false,
        comments = listOf(comment, comment, comment, comment, comment)
    )

    override suspend fun getNewsDetails(id: String): Flow<Pair<NewsDetailsModel?, ErrorStatus?>> =
        flow {
            emit(Pair(newsDetails, null))
        }
}
