package com.innoprog.android.feature.feed.newsdetails.data

import com.innoprog.android.feature.feed.newsdetails.data.network.CommentsResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.NewsDetailsResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.mapToCommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

@Suppress("Detekt.Indentation")
class NewsDetailsRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    NewsDetailsRepository {

    /*private val company = Company(
        "HighTechCorp",
        "CEO"
    )

    @Suppress("Detekt.StringLiteralDuplication")
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
        type = "project",
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
    )*/

    override suspend fun getNewsDetails(id: String): Resource<NewsDetailsModel> {
        return runCatching {
            val response = networkClient.getNewsDetails(id)
            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    Resource.Error(ErrorType.NO_CONNECTION)
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as NewsDetailsResponse) {
                        val newsDetails = results.map { it.mapToNewsDetailsModel }
                        Resource.Success(newsDetails)
                    }
                }

                else -> {
                    Resource.Error(ErrorType.UNEXPECTED)
                }
            }
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                Resource.Error(ErrorType.NO_CONNECTION)
            } else {
                Resource.Error(ErrorType.UNEXPECTED)
            }
        }
    }

    override fun getComments(newsId: String): Flow<Resource<List<CommentModel>>> = flow {
        val response = networkClient.getComments(newsId)
        runCatching {
            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    emit(Resource.Error(ErrorType.NO_CONNECTION))
                }
                ApiConstants.SUCCESS_CODE -> {
                    with(response as CommentsResponse) {
                        val data = results.map { it.mapToCommentModel() }
                        emit(Resource.Success(data))
                    }
                }
                else -> {
                    emit(Resource.Error(ErrorType.BAD_REQUEST))
                }
            }
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            } else {
                emit(Resource.Error(ErrorType.UNEXPECTED))
            }
        }

    }
}
