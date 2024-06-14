package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val service: ProfileApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {

        var response = Response()
        return withContext(Dispatchers.IO) {
            try {
                response = when (dto) {
                    is Request.GetProfile -> {
                        service.loadProfile()
                    }

                    is Request.GetProfileCompany -> {
                        service.loadProfileCompany()
                    }

                    is Request.GetAll -> {
                        IdeaResponse(service.getAll(authorId = dto.authorId))
                    }

                    is Request.GetProjects -> {
                        ProjectResponse(service.getProjects(authorId = dto.authorId))
                    }

                    is Request.GetIdeas -> {
                        IdeaResponse(service.getIdeas(type = IDEA, authorId = dto.authorId))
                    }

                    is Request.GetLikes -> {
                        IdeaResponse(service.getLikes(pageSize = PAGE_SIZE))
                    }

                    is Request.GetFavorites -> {
                        IdeaResponse(service.getFavorites(pageSize = PAGE_SIZE))
                    }

                    else -> {
                        throw IllegalArgumentException("Unsupported request type")
                    }
                }
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (exception: HttpException) {
                response.apply { resultCode = exception.code() }
            }
        }
    }

    companion object {

        private const val IDEA = "IDEA"
        private const val PAGE_SIZE = 50
    }
}
