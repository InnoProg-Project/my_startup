package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.profile.profiledetails.data.network.IdeaResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.ProjectResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.Request
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.feature.profile.profiledetails.domain.models.Project
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChipsProfileRepoImpl @Inject constructor(
    private val network: NetworkClient
) : ChipsProfileRepo {

    override suspend fun getAll(authorId: String): Flow<Resource<List<FeedWrapper>>> = flow {
        val response = network.doRequest(Request.GetAll(authorId))

        if (response is IdeaResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            val list: List<FeedWrapper> = response.results.map {
                when (it.type) {
                    IDEA -> FeedWrapper.Idea(
                        it.id,
                        it.type,
                        it.author,
                        it.projectId,
                        it.title,
                        it.content,
                        it.publishedAt,
                        it.likesCount,
                        it.commentsCount,
                        it.attachments,
                        it.isLiked,
                        it.isFavorite
                    )

                    NEWS -> FeedWrapper.News(
                        it.id,
                        it.type,
                        it.author,
                        it.projectId,
                        it.title,
                        it.content,
                        it.publishedAt,
                        it.likesCount,
                        it.commentsCount,
                        it.attachments,
                        it.isLiked,
                        it.isFavorite
                    )

                    else -> throw IllegalArgumentException("Unknown feed item type: ${it.type}")
                }
            }
            emit(Resource.Success(list))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    override suspend fun getProjects(userId: String): Flow<Resource<List<Project>>> =
        flow {
            val response = network.doRequest(Request.GetProjects(userId))

            if (response is ProjectResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val projectList: List<Project> = response.results.map {
                    Project(
                        it.id,
                        it.name,
                        it.shortDescription,
                        it.description,
                        it.logoFilePath,
                        it.publicationsCount,
                        it.area,
                        it.financingStage,
                        it.deadline,
                        it.siteUrls,
                        it.documentUrls,
                        it.projectAttachments
                    )
                }
                emit(Resource.Success(projectList))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }

    override suspend fun getIdeas(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWrapper.Idea>>> = flow {
        val response = network.doRequest(Request.GetIdeas(type, userId))

        if (response is IdeaResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            val ideaList: List<FeedWrapper.Idea> = response.results.map {
                FeedWrapper.Idea(
                    it.id,
                    it.type,
                    it.author,
                    it.projectId,
                    it.title,
                    it.content,
                    it.publishedAt,
                    it.likesCount,
                    it.commentsCount,
                    it.attachments,
                    it.isLiked,
                    it.isFavorite
                )
            }
            emit(Resource.Success(ideaList))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    override suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWrapper>>> =
        flow {
            val response = network.doRequest(Request.GetLikes(pageSize))

            if (response is IdeaResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val likesList: List<FeedWrapper> = response.results.map {
                    when (it.type) {
                        IDEA -> FeedWrapper.Idea(
                            it.id,
                            it.type,
                            it.author,
                            it.projectId,
                            it.title,
                            it.content,
                            it.publishedAt,
                            it.likesCount,
                            it.commentsCount,
                            it.attachments,
                            it.isLiked,
                            it.isFavorite
                        )

                        NEWS -> FeedWrapper.News(
                            it.id,
                            it.type,
                            it.author,
                            it.projectId,
                            it.title,
                            it.content,
                            it.publishedAt,
                            it.likesCount,
                            it.commentsCount,
                            it.attachments,
                            it.isLiked,
                            it.isFavorite
                        )

                        else -> throw IllegalArgumentException("Unknown feed item type: ${it.type}")
                    }
                }
                emit(Resource.Success(likesList))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }

    override suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWrapper>>> =
        flow {
            val response = network.doRequest(Request.GetFavorites(pageSize))

            if (response is IdeaResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val favList: List<FeedWrapper> = response.results.map {
                    when (it.type) {
                        IDEA -> FeedWrapper.Idea(
                            it.id,
                            it.type,
                            it.author,
                            it.projectId,
                            it.title,
                            it.content,
                            it.publishedAt,
                            it.likesCount,
                            it.commentsCount,
                            it.attachments,
                            it.isLiked,
                            it.isFavorite
                        )

                        NEWS -> FeedWrapper.News(
                            it.id,
                            it.type,
                            it.author,
                            it.projectId,
                            it.title,
                            it.content,
                            it.publishedAt,
                            it.likesCount,
                            it.commentsCount,
                            it.attachments,
                            it.isLiked,
                            it.isFavorite
                        )

                        else -> throw IllegalArgumentException("Unknown feed item type: ${it.type}")
                    }
                }
                emit(Resource.Success(favList))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }

    private fun getErrorType(code: Int): ErrorType = when (code) {
        ApiConstants.NO_CONNECTION -> ErrorType.NO_CONNECTION
        ApiConstants.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        ApiConstants.CAPTCHA_REQUIRED -> ErrorType.CAPTCHA_REQUIRED
        ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }

    companion object {

        private const val IDEA = "IDEA"
        private const val NEWS = "NEWS"
    }
}
