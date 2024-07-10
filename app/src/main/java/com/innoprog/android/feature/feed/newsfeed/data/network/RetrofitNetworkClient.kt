package com.innoprog.android.feature.feed.newsfeed.data.network

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
    private val apiService: FeedApi,
    private val context: Context
) :
    NetworkClient {

    override suspend fun loadNewsFeed(): Response {
        if (context.checkInternetReachability().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.loadNewsFeed()
                FeedResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }

    override suspend fun getProjectDetails(projectId: String): Response {
        if (context.checkInternetReachability().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProjectDetails(projectId)
                ProjectResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }

    private fun Context.checkInternetReachability(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }

        return activeNetwork?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
