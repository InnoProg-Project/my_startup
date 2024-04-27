package com.innoprog.android.feature.profile.editingprofile.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.innoprog.android.feature.profile.editingprofile.data.NetworkClient
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val service: ApiService,
) : NetworkClient {

    override suspend fun editProfile(): Response {

        return withContext(Dispatchers.IO) {
            try {
                val response = service.editProfile()
                response.apply { resultCode = SUCCESS }

            } catch (e: Exception) {
                Log.d("response", e.toString())
                Response().apply { resultCode = ERROR_CODE }
            }
        }

//    private fun isConnected(): Boolean {
//        val connectivityManager = context.getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            when {
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> return true
//            }
//        }
//        return false
//    }

    }
    companion object {
        const val ERROR_CODE = 500
        const val SUCCESS = 200
    }
}