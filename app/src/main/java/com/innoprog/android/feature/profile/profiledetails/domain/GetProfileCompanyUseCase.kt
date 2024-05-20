package com.innoprog.android.feature.profile.profiledetails.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetProfileCompanyUseCase {
    suspend fun getProfileCompany(): Flow<Resource<ProfileCompany>>
}
