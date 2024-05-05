package com.innoprog.android.feature.profile.editingprofile.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.http.HttpException
import android.os.Build

import android.util.Log
import androidx.annotation.RequiresExtension
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val service: ApiService,
    private val context: Context
) : NetworkClient {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun editProfile(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = service.editProfile()
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (e: IOException) {
                Log.d("response", "IOException: ${e.message}")
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            } catch (e: HttpException) {
                Log.d("response", "HttpException: ${e.message}")
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun editProfileCompany(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = service.editProfileCompany()
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (e: IOException) {
                Log.d("response", "IOException: ${e.message}")
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            } catch (e: HttpException) {
                Log.d("response", "HttpException: ${e.message}")
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
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> return true
            }
        }
        return false
    }

    companion object {
        const val ERROR_CODE = 500
        const val SUCCESS = 200
    }
}
