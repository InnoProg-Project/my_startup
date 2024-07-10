package com.innoprog.android.feature.profile.editingprofile.domain.impl

import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileCompanyUseCaseImpl @Inject constructor(private val repository: EditProfileInfoRepo) :
    EditProfileCompanyUseCase {

    override suspend fun editProfileCompany(name: String, url: String, role: String): Flow<Resource<ProfileCompany>> {
        return repository.editProfileCompany(name, url, role)
    }
}
