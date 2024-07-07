package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRetrofitClient @Inject constructor(
    private val service: ProfileApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        var response = Response()
        return withContext(Dispatchers.IO) {
            try {
                response = getResponse(dto)
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (exception: HttpException) {
                response.apply { resultCode = exception.code() }
            }
        }
    }

    private suspend fun getResponse(dto: Any): Response {
        return when (dto) {
            is RequestByProfile.GetProfile -> {
                service.loadProfile()
            }

            is RequestByProfile.GetProfileCompany -> {
                service.loadProfileCompany()
            }

            is RequestByProfile.GetAll -> {
                ChipsResponse(service.getAll(authorId = dto.authorId))
            }

            is RequestByProfile.GetProjects -> {
                ChipsResponse(service.getProjects(type = PublicationType.NEWS.value, authorId = dto.authorId))
            }

            is RequestByProfile.GetIdeas -> {
                ChipsResponse(service.getIdeas(type = PublicationType.IDEA.value, authorId = dto.authorId))
            }

            is RequestByProfile.GetLikes -> {
                ChipsResponse(service.getLikes(pageSize = PAGE_SIZE))
            }

            is RequestByProfile.GetFavorites -> {
                ChipsResponse(service.getFavorites(pageSize = PAGE_SIZE))
            }

            else -> {
                throw IllegalArgumentException("Unsupported request type")
            }
        }
    }

    companion object {

        private const val PAGE_SIZE = 50
    }
}
