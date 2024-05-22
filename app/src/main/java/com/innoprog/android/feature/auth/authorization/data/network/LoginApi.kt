package com.innoprog.android.feature.auth.authorization.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/v1/login")
    suspend fun authorize(
        @Body body: AuthorizationBody,
    ): LoginResponse
}
