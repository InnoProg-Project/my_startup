package com.innoprog.android.feature.profile.profiledetails.data.network

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
            is Request.GetProfile -> {
                service.loadProfile()
            }

            is Request.GetProfileCompany -> {
                service.loadProfileCompany()
            }

            is Request.GetAll -> {
                ChipsResponse(service.getAll(authorId = dto.authorId))
            }

            is Request.GetProjects -> {
                ChipsResponse(service.getProjects(type = NEWS, authorId = dto.authorId))
            }

            is Request.GetIdeas -> {
                ChipsResponse(service.getIdeas(type = IDEA, authorId = dto.authorId))
            }

            is Request.GetLikes -> {
                ChipsResponse(service.getLikes(pageSize = PAGE_SIZE))
            }

            is Request.GetFavorites -> {
                ChipsResponse(service.getFavorites(pageSize = PAGE_SIZE))
            }

            else -> {
                throw IllegalArgumentException("Unsupported request type")
            }
        }
    }

    companion object {

        private const val IDEA = "IDEA"
        private const val NEWS = "NEWS"
        private const val PAGE_SIZE = 50
    }
}
