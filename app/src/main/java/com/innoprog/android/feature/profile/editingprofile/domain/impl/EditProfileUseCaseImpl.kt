package com.innoprog.android.feature.profile.editingprofile.domain.impl

import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileInfoRepo
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileUseCaseImpl @Inject constructor(val repository: EditProfileInfoRepo): EditProfileUseCase {

    override suspend fun editProfile(name: String, about: String): Flow<Resource<Profile>> {
        return repository.editProfile(name, about)
    }
}
