package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface EditProfileCompanyUseCase {
    suspend fun editProfileCompany(): Flow<Resource<ProfileCompany>>
}
