package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface EditProfileInfoRepo {
    suspend fun editProfile(name: String, about: String): Flow<Resource<Profile>>
    suspend fun editProfileCompany(name: String, url: String, role: String): Flow<Resource<ProfileCompany>>
}
