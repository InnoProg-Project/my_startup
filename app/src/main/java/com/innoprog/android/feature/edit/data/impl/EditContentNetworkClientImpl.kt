package com.innoprog.android.feature.edit.data.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import com.innoprog.android.feature.edit.data.EditContentNetworkClient
import com.innoprog.android.feature.edit.data.dto.EditDto
import com.innoprog.android.feature.edit.data.network.EditContentApi
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EditContentNetworkClientImpl @Inject constructor(
    private val api: EditContentApi,
    private val context: Context
) : EditContentNetworkClient {

    override suspend fun createIdea(dto: EditDto): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = api.createContent(dto)
                response.apply { resultCode = SUCCESS_CODE }
            } catch (e: IOException) {
                Log.d("response", "IOException: ${e.message}")
                Response().apply { resultCode = BAD_REQUEST_CODE }
            } catch (e: HttpException) {
                Log.d("response", "HttpException: ${e.message}")
                Response().apply { resultCode = BAD_REQUEST_CODE }
            }
        }
    }

    //override suspend fun getProject()

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.let {
            it.hasTransport(TRANSPORT_CELLULAR) ||
                    it.hasTransport(TRANSPORT_WIFI) ||
                    it.hasTransport(TRANSPORT_ETHERNET)
        } ?: false
    }

    companion object {
        const val SUCCESS_CODE = 200
        const val NO_INTERNET_CONNECTION_CODE = -1
        const val BAD_REQUEST_CODE = 400
    }

}