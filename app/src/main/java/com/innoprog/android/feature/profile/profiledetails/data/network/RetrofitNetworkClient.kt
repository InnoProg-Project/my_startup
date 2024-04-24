package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class RetrofitNetworkClient @Inject constructor(
    private val context: Context,
    private val service: ApiService,
) : NetworkClient {

    override suspend fun getProfile(): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = service.loadProfile()
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (e: Throwable) {
                Response().apply { resultCode = ApiConstants.INTERNAL_SERVER_ERROR }
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
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> return true
            }
        }
        return false
    }
}
