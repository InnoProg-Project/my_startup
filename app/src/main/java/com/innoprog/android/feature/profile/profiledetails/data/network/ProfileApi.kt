package com.innoprog.android.feature.profile.profiledetails.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("/v1/profile")
    suspend fun loadProfile(): ProfileResponse

    @GET("/v1/profile/{profileId}")
    suspend fun loadProfileById(@Path("profileId") id: String): ProfileResponse

    @GET("/v1/profile/company")
    suspend fun loadProfileCompany(): ProfileCompanyResponse
}
