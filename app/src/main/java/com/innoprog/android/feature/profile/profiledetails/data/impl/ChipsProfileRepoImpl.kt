package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.profile.profiledetails.data.dto.model.FeedDto
import com.innoprog.android.feature.profile.profiledetails.data.dto.model.ProjectDto
import com.innoprog.android.feature.profile.profiledetails.data.dto.model.mapToDomain
import com.innoprog.android.feature.profile.profiledetails.data.network.ChipsResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.RequestByProfile
import com.innoprog.android.feature.profile.profiledetails.domain.ChipsProfileRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWithProject
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

    override suspend fun getAll(authorId: String): Flow<Resource<List<FeedWithProject>>> {
        return getResult<ChipsResponse, List<FeedWithProject>>(
            getResponse = { network.doRequest(RequestByProfile.GetAll(authorId)) },
            mapToDomain = { response -> mapToFeedWithProject(response.results) }
        )
    }

    override suspend fun getProjects(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWithProject>>> {
        return getResult<ChipsResponse, List<FeedWithProject>>(
            getResponse = { network.doRequest(RequestByProfile.GetProjects(type, userId)) },
            mapToDomain = { response -> mapToFeedWithProject(response.results) }
        )
    }

    override suspend fun getIdeas(
        type: String,
        userId: String
    ): Flow<Resource<List<FeedWithProject>>> {
        return getResult<ChipsResponse, List<FeedWithProject>>(
            getResponse = { network.doRequest(RequestByProfile.GetIdeas(type, userId)) },
            mapToDomain = { response -> mapToFeedWithProject(response.results) }
        )
    }

    override suspend fun getLikes(pageSize: Int): Flow<Resource<List<FeedWithProject>>> {
        return getResult<ChipsResponse, List<FeedWithProject>>(
            getResponse = { network.doRequest(RequestByProfile.GetLikes(pageSize)) },
            mapToDomain = { response -> mapToFeedWithProject(response.results) }
        )
    }

    override suspend fun getFavorites(pageSize: Int): Flow<Resource<List<FeedWithProject>>> {
        return getResult<ChipsResponse, List<FeedWithProject>>(
            getResponse = { network.doRequest(RequestByProfile.GetFavorites(pageSize)) },
            mapToDomain = { response -> mapToFeedWithProject(response.results) }
        )
    }

    private suspend fun mapToFeedWithProject(publicationList: List<FeedDto>): List<FeedWithProject> {
        return publicationList.map { item ->
            val publication = item.mapToDomain()
            if (publication is FeedWrapper.News) {
                val projectResponse = runCatching {
                    network.doRequest(
                        RequestByProfile.GetProjectById(id = publication.projectId)
                    )
                }.getOrNull()
                val project = if (projectResponse is ProjectDto) {
                    projectResponse.mapToDomain()
                } else {
                    null
                }
                FeedWithProject(publication, project)
            } else {
                FeedWithProject(publication, null)
            }
        }
    }

    private inline fun <reified Data, reified Domain> getResult(
        crossinline getResponse: suspend () -> Response,
        crossinline mapToDomain: suspend (Data) -> Domain
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
