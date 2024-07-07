package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.profile.profiledetails.data.dto.model.mapToDomain
import com.innoprog.android.feature.profile.profiledetails.data.network.ChipsResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.RequestByProfile
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChipsProfileRepoImpl @Inject constructor(
    private val network: NetworkClient
) : ChipsProfileRepo {

    override suspend fun getAll(authorId: String): Flow<Resource<List<FeedWrapper>>> {
        return getResult<ChipsResponse, List<FeedWrapper>>(
            getResponse = { network.doRequest(RequestByProfile.GetAll(authorId)) },
            mapToDomain = { response -> response.results.map { item -> item.mapToDomain() } }
        )
    }

    override suspend fun getProjects(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWrapper.News>>> {
        return getResult<ChipsResponse, List<FeedWrapper.News>>(
            getResponse = { network.doRequest(RequestByProfile.GetProjects(type, userId)) },
            mapToDomain = { response -> response.results.map { item -> item.mapToDomain() as FeedWrapper.News } }
        )
    }

    override suspend fun getIdeas(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWrapper.Idea>>> {
        return getResult<ChipsResponse, List<FeedWrapper.Idea>>(
            getResponse = { network.doRequest(RequestByProfile.GetIdeas(type, userId)) },
            mapToDomain = { response -> response.results.map { item -> item.mapToDomain() as FeedWrapper.Idea } }
        )
    }

    override suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return getResult<ChipsResponse, List<FeedWrapper>>(
            getResponse = { network.doRequest(RequestByProfile.GetLikes(pageSize)) },
            mapToDomain = { response -> response.results.map { item -> item.mapToDomain() } }
        )
    }

    override suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWrapper>>> {
        return getResult<ChipsResponse, List<FeedWrapper>>(
            getResponse = {network.doRequest(RequestByProfile.GetFavorites(pageSize)) },
            mapToDomain = { response -> response.results.map { item -> item.mapToDomain() } }
        )
    }

    private inline fun <reified Data, reified Domain> getResult(
        crossinline getResponse: suspend () -> Response,
        crossinline mapToDomain: (Data) -> Domain
    ): Flow<Resource<Domain>> {
        return flow {
            val response = getResponse()
            if (response is Data && response.resultCode == ApiConstants.SUCCESS_CODE) {
                emit(Resource.Success(mapToDomain(response)))
            } else {
                emit(Resource.Error(getErrorType(response.resultCode)))
            }
        }
    }

    private fun getErrorType(code: Int): ErrorType = when (code) {
        ApiConstants.NO_INTERNET_CONNECTION_CODE -> ErrorType.NO_CONNECTION
        ApiConstants.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        ApiConstants.FORBIDDEN -> ErrorType.CAPTCHA_REQUIRED
        ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }
}
