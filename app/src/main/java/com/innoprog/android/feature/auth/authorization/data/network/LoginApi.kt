package com.innoprog.android.feature.auth.authorization.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LoginApi {

    @GET("/v1/profile")
    suspend fun authorize(
        @Header("X-Authorization") header: String
    ): Response<LoginResponse>
}
