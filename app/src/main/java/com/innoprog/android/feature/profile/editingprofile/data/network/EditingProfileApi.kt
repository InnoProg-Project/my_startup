package com.innoprog.android.feature.profile.editingprofile.data.network

import com.innoprog.android.feature.profile.common.ProfileCompanyResponse
import com.innoprog.android.feature.profile.common.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface EditingProfileApi {

    @PUT("/v1/profile")
    suspend fun editProfile(
        @Body body: EditingProfileBody
    ): ProfileResponse

    @PUT("/v1/profile/company")
    suspend fun editProfileCompany(
        @Body body: EditingProfileCompanyBody
    ): ProfileCompanyResponse
}
