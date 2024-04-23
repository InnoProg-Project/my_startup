package com.innoprog.android.feature.auth.authorization.data

import com.innoprog.android.network.data.ApiService
import com.innoprog.android.network.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val api: ApiService,
) : NetworkClient {

    override suspend fun authorize(dto: AuthorizationBody): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.authorize(dto)
                response.apply { resultCode = SUCCESS }
            } catch (e: Exception) {
                Response().apply { resultCode = ERROR_CODE }
            }
        }
    }

    companion object {
        const val ERROR_CODE = 500
        const val SUCCESS = 200
    }
}
