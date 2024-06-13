package com.innoprog.android.feature.auth.registration.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {
    @POST("/v1/registration")
    suspend fun setRegistrationData(@Body body: RegistrationBody): Response<RegistrationResponse>
}
