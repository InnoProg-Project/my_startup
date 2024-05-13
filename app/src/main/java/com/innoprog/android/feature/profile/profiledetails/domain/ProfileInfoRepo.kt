package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileInfoRepo {
    suspend fun loadProfile(): Flow<Resource<Profile>>
    suspend fun loadProfileCompany(): Flow<Resource<ProfileCompany>>
}
