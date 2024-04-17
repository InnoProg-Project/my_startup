package com.innoprog.android.feature.auth.authorization.data

import android.util.Log
import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val api: ApiService,
) : NetworkClient {

    override suspend fun authorize(dto: AuthorizationBody): Response {
        return try {
            val response = api.authorize(dto)
            Log.d("response", response.toString())
            response.apply { resultCode = 200 }

        } catch (e: Throwable) {
            Log.d("response", e.toString())
            Response().apply { resultCode = 500 }
        }
    }
}
