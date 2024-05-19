package com.innoprog.android.feature.profile.editingprofile.domain


import com.innoprog.android.feature.profile.editingprofile.domain.models.Profile
import com.innoprog.android.feature.profile.editingprofile.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveProfile(): Flow<Resource<Profile>>
    suspend fun saveProfileCompany(): Flow<Resource<ProfileCompany>>
}