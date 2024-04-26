package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.data.network.ProfileDto

interface ProfileUseCase {
    suspend fun saveProfile(): ProfileDto
}