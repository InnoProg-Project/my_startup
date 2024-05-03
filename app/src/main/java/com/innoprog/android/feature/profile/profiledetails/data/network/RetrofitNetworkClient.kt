package com.innoprog.android.feature.profile.profiledetails.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val service: ApiService,
    private val context: Context,
) : NetworkClient {

    override suspend fun getProfile(): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = service.loadProfile()
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (e: IOException) {
                Log.e("RetrofitNetworkClient", "An error occurred", e)
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            }
        }
    }

    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) || it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_BLUETOOTH)
        } ?: false
    }
}
