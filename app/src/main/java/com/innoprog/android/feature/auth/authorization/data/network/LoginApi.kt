package com.innoprog.android.feature.auth.authorization.data.network

import retrofit2.Response
import retrofit2.http.GET

interface LoginApi {
    @GET("/v1/profile")
    suspend fun authorize(): Response<LoginResponse>
}
