package com.innoprog.android.feature.training.courseInformation.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val courseInformationApi: CourseInformationApi,
    private val context: Context
) : NetworkClient {

    override suspend fun getCourseInformation(courseId: String): Response {
        if (!context.checkInternetAvailability()) {
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

    fun Context.checkInternetAvailability(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =
            connectivityManager.activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
                ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}