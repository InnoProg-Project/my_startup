package com.innoprog.android.feature.training.courseInformation.data.network

import android.content.Context
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val courseInformationApi: CourseInformationApi,
    private val context: Context
) : NetworkClient {

    override suspend fun getCourseInformation(courseId: String): Response {
        if (context.isInternetReachable().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = courseInformationApi.getCourseInformation(courseId)
                CourseInformationResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }
}