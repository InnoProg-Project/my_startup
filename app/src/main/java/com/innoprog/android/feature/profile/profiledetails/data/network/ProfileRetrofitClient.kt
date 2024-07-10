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
                getResponse(dto)
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

    private suspend fun getResponse(dto: Any): Response {
        return when (dto) {
            is RequestByProfile.GetProfile -> mapToResponse(service.loadProfile()) { it }

            is RequestByProfile.GetProfileCompany ->
                mapToResponse(service.loadProfileCompany()) { it }

            is RequestByProfile.GetAll -> mapToResponse(service.getAll(dto.authorId)) {
                ChipsResponse(
                    it
                )
            }

            is RequestByProfile.GetProjects -> {
                mapToResponse(
                    service.getProjects(
                        type = PublicationType.NEWS.value,
                        authorId = dto.authorId
                    )
                ) { ChipsResponse(it) }
            }

            is RequestByProfile.GetIdeas ->
                mapToResponse(
                    service.getIdeas(
                        type = PublicationType.IDEA.value,
                        authorId = dto.authorId
                    )
                ) { ChipsResponse(it) }

            is RequestByProfile.GetLikes ->
                mapToResponse(service.getLikes(pageSize = PAGE_SIZE)) {
                    ChipsResponse(it)
                }

            is RequestByProfile.GetFavorites ->
                mapToResponse(service.getFavorites(pageSize = PAGE_SIZE)) {
                    ChipsResponse(it)

                }

            is RequestByProfile.GetProjectById -> mapToResponse(service.getProjectById(dto.id)) { it }

            else -> throw IllegalArgumentException("Unsupported request type")
        }
    }

    private inline fun <reified D, reified R : Response> mapToResponse(
        response: retrofit2.Response<D>,
        mapToResponse: (D) -> R
    ): Response {
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            mapToResponse(body).apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = response.code() }
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
            else -> ApiConstants.INTERNAL_SERVER_ERROR
        }
        return createErrorResponse(errorCode)
    }

    companion object {

        private const val PAGE_SIZE = 50
    }
}
