package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
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
        return withContext(Dispatchers.IO) {
            try {
                val response = getResponse(dto)
                response.body()?.let {
                    Response().apply { resultCode = response.code() }
                } ?: Response().apply { resultCode = response.code() }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }

    private suspend fun getResponse(dto: Any): retrofit2.Response<out Any> {
        return when (dto) {
            is RequestByProfile.GetProfile -> {
                service.loadProfile()
            }

            is RequestByProfile.GetProfileCompany -> {
                service.loadProfileCompany()
            }

            is RequestByProfile.GetAll -> {
                service.getAll(authorId = dto.authorId)
            }

            is RequestByProfile.GetProjects -> {
                service.getProjects(type = PublicationType.NEWS.value, authorId = dto.authorId)
            }

            is RequestByProfile.GetIdeas -> {
                service.getIdeas(type = PublicationType.IDEA.value, authorId = dto.authorId)
            }

            is RequestByProfile.GetLikes -> {
                service.getLikes(pageSize = PAGE_SIZE)
            }

            is RequestByProfile.GetFavorites -> {
                service.getFavorites(pageSize = PAGE_SIZE)
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