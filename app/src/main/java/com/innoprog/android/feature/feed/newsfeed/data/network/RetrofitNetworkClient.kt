package com.innoprog.android.feature.feed.newsfeed.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: FeedApi,
    private val context: Context
) :
    NetworkClient {

    override suspend fun loadNewsFeed(): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.loadNewsFeed()
                FeedResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
