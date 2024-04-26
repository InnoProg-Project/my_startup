package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.feature.profile.editingprofile.data.network.ProfileApi
import com.innoprog.android.feature.profile.editingprofile.data.network.ProfileDto
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
) : ProfileRepository {

    override suspend fun saveProfile(): ProfileDto {
        val profileNet = profileApi.saveProfile()
        return ProfileDto(body = profileNet.body, userId = profileNet.userId)
    }
}