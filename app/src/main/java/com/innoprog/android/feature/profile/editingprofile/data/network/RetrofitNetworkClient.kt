package com.innoprog.android.feature.profile.editingprofile.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val service: ApiService,
    private val context: Context,
) : NetworkClient {

    override suspend fun getProfile(): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

//        return withContext(Dispatchers.IO) {
//            try {
//                val response = service.saveProfile()
//                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
//            } catch (e: IOException) {
//                Log.e("RetrofitNetworkClient", "An error occurred", e)
//                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
//            }
//        }
    }

    override suspend fun editProfile(): Response {
        TODO("Not yet implemented")
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