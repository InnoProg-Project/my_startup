package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.data.BodyResponse
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(private val profileRepository: ProfileRepository) : ProfileUseCase {
    override suspend fun saveProfile(): BodyResponse {
        return profileRepository.editProfile()
    }
}