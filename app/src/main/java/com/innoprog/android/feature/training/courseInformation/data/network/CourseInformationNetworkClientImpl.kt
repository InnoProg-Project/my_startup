package com.innoprog.android.feature.training.courseInformation.data.network

import android.content.Context
import com.google.gson.JsonParseException
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CourseInformationNetworkClientImpl @Inject constructor(
    private val courseInformationApi: CourseInformationApi,
    private val context: Context
) : CourseInformationNetworkClient {

    override suspend fun getCourseInformation(courseId: String): Response {
        if (context.isInternetReachable().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = courseInformationApi.getCourseInformation(courseId)
                processResponse(response)
            } catch (exception: HttpException) {
                logAndCreateErrorResponse(exception)
            } catch (exception: IOException) {
                logAndCreateErrorResponse(exception)
            } catch (exception: JsonParseException) {
                logAndCreateErrorResponse(exception)
            }
        }
    }

    private fun processResponse(response: retrofit2.Response<CourseInformationDto>): Response {
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            CourseInformationResponse(results = body).apply {
                resultCode = ApiConstants.SUCCESS_CODE
            }
        } else {
            Response().apply { resultCode = response.code() }
        }
    }

    private fun logAndCreateErrorResponse(exception: Throwable): Response {
        val errorCode = when (exception) {
            is HttpException -> exception.code()
            is IOException -> ApiConstants.NO_INTERNET_CONNECTION_CODE
            is JsonParseException -> ApiConstants.BAD_REQUEST_CODE
            else -> ApiConstants.INTERNAL_SERVER_ERROR
        }
        return Response().apply { resultCode = errorCode }
    }
}