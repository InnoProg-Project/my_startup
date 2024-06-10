package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.profile.profiledetails.data.network.NewsResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.ProjectResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.Request
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
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

    override suspend fun getAll(authorId: String): Flow<Resource<List<News>>> = flow {
        val response = network.doRequest(Request.GetAll(authorId))

        if (response is NewsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            val list: List<News> = response.results.map {
                News(
                    it.id,
                    it.type,
                    it.author,
                    it.projectId,
                    it.coverUrl,
                    it.title,
                    it.content,
                    it.publishedAt,
                    it.likesCount,
                    it.commentsCount
                )
            }
            emit(Resource.Success(list))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    override suspend fun getProjects(userId: String): Flow<Resource<List<Project>>> = flow {
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

    override suspend fun getIdeas(type: String, userId: String): Flow<Resource<List<News>>> = flow {
        val response = network.doRequest(Request.GetIdeas(type, userId))

        if (response is NewsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            val ideaList: List<News> = response.results.map {
                News(
                    it.id,
                    it.type,
                    it.author,
                    it.projectId,
                    it.coverUrl,
                    it.title,
                    it.content,
                    it.publishedAt,
                    it.likesCount,
                    it.commentsCount
                )
            }
            emit(Resource.Success(ideaList))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    override suspend fun getLikes(lastId: String, pageSize: Int): Flow<Resource<List<News>>> =
        flow {
            val response = network.doRequest(Request.GetLikes(lastId, pageSize))

            if (response is NewsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val likesList: List<News> = response.results.map {
                    News(
                        it.id,
                        it.type,
                        it.author,
                        it.projectId,
                        it.coverUrl,
                        it.title,
                        it.content,
                        it.publishedAt,
                        it.likesCount,
                        it.commentsCount
                    )
                }
                emit(Resource.Success(likesList))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }

    override suspend fun getFavorites(lastId: String, pageSize: Int): Flow<Resource<List<News>>> =
        flow {
            val response = network.doRequest(Request.GetFavorites(lastId, pageSize))

            if (response is NewsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val favList: List<News> = response.results.map {
                    News(
                        it.id,
                        it.type,
                        it.author,
                        it.projectId,
                        it.coverUrl,
                        it.title,
                        it.content,
                        it.publishedAt,
                        it.likesCount,
                        it.commentsCount
                    )
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
}
