package com.innoprog.android.feature.profile.profiledetails.data.network

import com.google.gson.JsonParseException
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProfileRetrofitClient @Inject constructor(
    private val service: ProfileApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = getResponse(dto)
                if (response.isSuccessful) {
                    Response().apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            } catch (exception: IOException) {
                logAndCreateErrorResponse(exception)
            } catch (exception: JsonParseException) {
                logAndCreateErrorResponse(exception)
            } catch (exception: SocketTimeoutException) {
                logAndCreateErrorResponse(exception)
            }
        }
    }

    private suspend fun getResponse(dto: Any): retrofit2.Response<*> {
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

    private fun createErrorResponse(code: Int): Response {
        return Response().apply {
            resultCode = code
        }
    }

    private fun logAndCreateErrorResponse(exception: Throwable): Response {
        val errorCode = when (exception) {
            is HttpException -> exception.code()
            is IOException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            is JsonParseException -> ApiConstants.BAD_REQUEST_CODE
            is SocketTimeoutException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            else -> ApiConstants.INTERNAL_SERVER_ERROR
        }
        return createErrorResponse(errorCode)
    }

    companion object {

        private const val PAGE_SIZE = 50
    }
}
