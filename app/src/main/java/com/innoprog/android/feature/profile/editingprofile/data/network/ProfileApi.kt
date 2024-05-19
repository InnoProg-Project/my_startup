package com.innoprog.android.feature.profile.editingprofile.data.network

import retrofit2.http.PUT

interface ProfileApi {

    @PUT("/v1/profile")
    suspend fun saveProfile(): ProfileResponse

    @PUT("/v1/profile/company")
    suspend fun saveProfileCompany(): ProfileCompanyResponse
}