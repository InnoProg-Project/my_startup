package com.innoprog.android.feature.projects.projectsScreen.data.network

import android.content.Context
import com.google.gson.JsonParseException
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileDao
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProjectListNetworkClientImpl @Inject constructor(
    private val apiService: ProjectApiService,
    private val context: Context,
    private val profileDao: ProfileDao
) : ProjectListNetworkClient {

    override suspend fun getProjectList(): Response {
        if (context.isInternetReachable().not()) {
            return createErrorResponse(ApiConstants.NO_INTERNET_CONNECTION_CODE)
        }

        return withContext(Dispatchers.IO) {
            executeApiCall()
        }
    }

    private suspend fun executeApiCall(): Response {
        return try {
            val userId = getUserId()
            val response = apiService.getProjectList(
                userId,
                null,
                PROJECTS_AMOUNT_PER_PAGE_50
            )

            if (response.isSuccessful) {
                ProjectListResponse(result = response.body() ?: emptyList()).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } else {
                createErrorResponse(response.code())
            }
        } catch (exception: HttpException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: IOException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: JsonParseException) {
            logAndCreateErrorResponse(exception)
        } catch (exception: SocketTimeoutException) {
            logAndCreateErrorResponse(exception)
        }
    }

    private suspend fun getUserId(): String {
        val profile = profileDao.getProfile()
        return profile.userId
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

    private companion object {
        const val PROJECTS_AMOUNT_PER_PAGE_50 = 50
    }
}