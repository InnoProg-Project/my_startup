package com.innoprog.android.feature.profile.profiledetails.data.network

import retrofit2.http.GET

interface ProfileApi {

    @GET("/v1/profile")
    suspend fun loadProfile(): ProfileResponse

    @GET("/v1/profile/company")
    suspend fun loadProfileCompany(): ProfileCompanyResponse
}
