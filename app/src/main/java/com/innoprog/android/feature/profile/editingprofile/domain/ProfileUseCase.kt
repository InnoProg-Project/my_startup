package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.data.BodyResponse

interface ProfileUseCase {
    suspend fun saveProfile(): BodyResponse
}