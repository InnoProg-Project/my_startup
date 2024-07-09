package com.innoprog.android.feature.feed.newsdetails.data

import android.util.Log
import com.google.gson.JsonParseException
import com.innoprog.android.BuildConfig
import com.innoprog.android.feature.feed.newsdetails.data.converters.mapToCommentModel
import com.innoprog.android.feature.feed.newsdetails.data.converters.mapToNewsDetails
import com.innoprog.android.feature.feed.newsdetails.data.network.AddCommentResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.CommentsResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsdetails.data.network.NewsDetailsResponse
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.feature.feed.newsfeed.data.converters.mapToProjectDomain
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectResponse
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorHandler
import com.innoprog.android.util.ErrorHandlerImpl
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NewsDetailsRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    NewsDetailsRepository {
    private val errorHandler: ErrorHandler = ErrorHandlerImpl()

    override suspend fun getNewsDetails(id: String): Resource<NewsDetailsModel> {
        return try {
            val response = networkClient.getNewsDetails(id)
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val newsDetails = (response as NewsDetailsResponse).results.mapToNewsDetails()
                    val projectDetails = newsDetails.projectId?.let { loadProjectDetails(it) }
                    val newsDetailsWithProject = newsDetails.copy(project = projectDetails)

                    Resource.Success(newsDetailsWithProject)
                }

                else -> errorHandler.handleErrorCode(response.resultCode)
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.toString())
            errorHandler.handleHttpException(e)
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JsonParseException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
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
                        val comments = results.map { it.mapToCommentModel() }
                        emit(Resource.Success(comments))
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

    private suspend fun loadProjectDetails(projectId: String): Project? {
        val projectResponse = networkClient.getProjectDetails(projectId)

        return if (projectResponse.resultCode == ApiConstants.SUCCESS_CODE) {
            runCatching {
                (projectResponse as ProjectResponse).results.mapToProjectDomain()
            }.onFailure { exception ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "mapping error -> $exception")
                    exception.printStackTrace()
                }
            }.getOrNull()
        } else {
            null
        }
    }

    override suspend fun addComment(
        publicationId: String,
        content: String
    ): Resource<CommentModel> {
        return try {
            val response = networkClient.addComment(publicationId, content)
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val commentResponse =
                        (response as AddCommentResponse).result.mapToCommentModel()
                    Resource.Success(commentResponse)
                }

                else -> errorHandler.handleErrorCode(response.resultCode)
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.toString())
            errorHandler.handleHttpException(e)
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JsonParseException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.toString())
            Resource.Error(ErrorType.NO_CONNECTION)
        }
    }

    companion object {
        private val TAG = NewsDetailsRepository::class.simpleName
    }
}
