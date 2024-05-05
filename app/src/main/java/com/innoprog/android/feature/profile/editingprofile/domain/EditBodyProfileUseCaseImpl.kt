package com.innoprog.android.feature.profile.editingprofile.domain

import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditBodyProfileUseCaseImpl @Inject constructor(private val profileRepository: ProfileRepository) :
    EditBodyProfileUseCase {
    override suspend fun editProfile(): Flow<Resource<Body>> {
        return profileRepository.editProfile()
    }
}
