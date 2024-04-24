package com.innoprog.android.feature.profile.profiledetails.data.network

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
) : NetworkClient {

    override suspend fun getProfile(): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.loadProfile()
                response.apply { resultCode = ApiConstants.SUCCESS_CODE }
            } catch (e: IOException) {
                Log.e("RetrofitNetworkClient", "An error occurred", e)
                Response().apply { resultCode = ApiConstants.INTERNAL_SERVER_ERROR }
            }
        }
    }
}
