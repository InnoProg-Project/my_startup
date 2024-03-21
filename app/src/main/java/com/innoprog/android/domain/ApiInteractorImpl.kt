package com.innoprog.android.domain

import com.innoprog.android.data.network.ApiService
import dagger.internal.Provider
import okhttp3.ResponseBody
import retrofit2.Call

class ApiInteractorImpl(
    apiMethodsProvider: Provider<ApiService>,
) : ApiInteractor {

    private val apiMethods = apiMethodsProvider.provide()

    override fun post(url: String, body: Any, headers: Map<String, String>): Call<ResponseBody> {
        val response = when (body) {
            is String -> apiMethods.postRaw(url = url, body = body, headers = headers)
            else -> apiMethods.post(url = url, body = body, headers = headers)
        }.execute()

        return response
    }

    override fun get(url: String, headers: Map<String, String>): Call<ResponseBody> {
        val response = apiMethods.get(url = url, headers = headers).execute()
        return response
    }
}
