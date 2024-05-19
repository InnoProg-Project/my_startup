package com.innoprog.android.feature.profile.editingprofile.domain.impl

import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProfileCompanyUseCaseImpl @Inject constructor(val repository: ProfileRepository) :
    SaveProfileCompanyUseCase {
    override suspend fun saveProfileCompany(): Flow<Resource<ProfileCompany>> {
        return repository.saveProfileCompany()
    }
}