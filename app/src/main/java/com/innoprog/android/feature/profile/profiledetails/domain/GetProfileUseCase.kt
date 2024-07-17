package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetProfileUseCase {
    suspend fun getProfile(userId: String = ""): Flow<Resource<Profile>>
}
