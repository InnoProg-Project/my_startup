package com.innoprog.android.feature.profile.editingprofile.domain.impl

import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProfileUseCaseImpl @Inject constructor(val repository: ProfileRepository) :
    SaveProfileUseCase {
    override suspend fun saveProfile(): Flow<Resource<Profile>> {
        return repository.saveProfile()
    }
}
