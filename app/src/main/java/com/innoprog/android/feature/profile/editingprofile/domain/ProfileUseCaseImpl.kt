package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.data.network.ProfileDto
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(private val profileRepository: ProfileRepository) : ProfileUseCase {
    override suspend fun saveProfile(): ProfileDto {
        return profileRepository.saveProfile()
    }
}