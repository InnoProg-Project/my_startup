package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.domain.model.CompanyBody
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileCompanyUseCaseImpl @Inject constructor(private val profileRepository: ProfileRepository) :
    EditProfileCompanyUseCase {
    override suspend fun editProfileCompany(): Flow<Resource<CompanyBody>> {
        return profileRepository.editProfileCompany()
    }
}
