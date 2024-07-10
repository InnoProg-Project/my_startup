package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface EditProfileUseCase {
    suspend fun editProfile(name: String, about: String): Flow<Resource<Profile>>
}
