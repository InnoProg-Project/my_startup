package com.innoprog.android.feature.profile.editingprofile.data


import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.network.data.ApiService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ApiService,
) : ProfileRepository {

    override suspend fun editProfile(): BodyResponse {
        val profileNet = profileApi.editProfile()
        return BodyResponse(name = profileNet.name, about = profileNet.about)
    }
}