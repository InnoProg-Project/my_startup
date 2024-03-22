package com.innoprog.android.network.domain

import okhttp3.ResponseBody
import retrofit2.Call

interface ApiInteractor {
    fun post(url: String, body: Any, headers: Map<String, String>): Call<ResponseBody>

    fun get(url: String, headers: Map<String, String>): Call<ResponseBody>
}
