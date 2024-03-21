package com.innoprog.android.domain

import com.innoprog.android.data.network.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class ApiInteractorImpl @Inject constructor(
    private val apiService: ApiService
) : ApiInteractor {

    override fun post(url: String, body: Any, headers: Map<String, String>): Call<ResponseBody> {
        val requestBody = RequestBody.create("text/plain".toMediaType(), body.toString())
        return apiService.post(url, requestBody, headers)
    }

    override fun get(url: String, headers: Map<String, String>): Call<ResponseBody> {
        return apiService.get(url, headers)
    }
}
