package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.data.BodyResponse

interface ProfileRepository {
    suspend fun editProfile(): BodyResponse
}