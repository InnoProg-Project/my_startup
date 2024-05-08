package com.innoprog.android.feature.auth.authorization.data.network

import android.util.Log
import com.innoprog.android.network.data.ApiConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val api: LoginApi,
) : NetworkClient {

    override suspend fun authorize(dto: AuthorizationBody): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.authorize(dto)
                if (response.code() == ApiConstants.SUCCESS_CODE) {
                    val resultHeaders = response.headers()
                    Log.d("response", "An error $resultHeaders")
                    response.body()?.apply {
                        resultCode = ApiConstants.SUCCESS_CODE; headers = resultHeaders.toString()
                    }
                } else {
                    Log.d("response", "An error ${response.code()}")
                    Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
                }
            } catch (e: IOException) {
                Log.e("RetrofitNetworkClient", "An error occurred", e)
                Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
            }!!
        }
    }
}
