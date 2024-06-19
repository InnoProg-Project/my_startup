package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileCompanyResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface EditingProfileApi {

    @PUT("/v1/profile")
    suspend fun saveProfile(
        @Body body: EditingProfileBody
    ): ProfileResponse

    @PUT("/v1/profile/company")
    suspend fun saveProfileCompany(
        @Body body: EditingProfileCompanyBody
    ): ProfileCompanyResponse
}
