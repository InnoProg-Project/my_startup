package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.profile.profiledetails.data.dto.model.mapToDomain
import com.innoprog.android.feature.profile.profiledetails.data.network.ChipsResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.Request
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
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

    override suspend fun getAll(authorId: String): Flow<Resource<List<FeedWrapper>>> {
        return flow {
            val response = network.doRequest(Request.GetAll(authorId))

            if (response is ChipsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                emit(Resource.Success(response.results.map { it.mapToDomain() }))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    override suspend fun getProjects(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWrapper.News>>> {
        return flow {
            val response = network.doRequest(Request.GetProjects(type, userId))

            if (response is ChipsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val projects = response.results.map {
                    it.mapToDomain() as FeedWrapper.News
                }
                emit(Resource.Success(projects))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    override suspend fun getIdeas(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWrapper.Idea>>> {
        return flow {
            val response = network.doRequest(Request.GetIdeas(type, userId))

            if (response is ChipsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                val ideas = response.results.map {
                    it.mapToDomain() as FeedWrapper.Idea
                }
                emit(Resource.Success(ideas))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    override suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return flow {
            val response = network.doRequest(Request.GetLikes(pageSize))

            if (response is ChipsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                emit(Resource.Success(response.results.map { it.mapToDomain() }))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    override suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return flow {
            val response = network.doRequest(Request.GetFavorites(pageSize))

            if (response is ChipsResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
                emit(Resource.Success(response.results.map { it.mapToDomain() }))
            } else
                emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    private fun getErrorType(code: Int): ErrorType = when (code) {
        ApiConstants.NO_CONNECTION -> ErrorType.NO_CONNECTION
        ApiConstants.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        ApiConstants.CAPTCHA_REQUIRED -> ErrorType.CAPTCHA_REQUIRED
        ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }
}
