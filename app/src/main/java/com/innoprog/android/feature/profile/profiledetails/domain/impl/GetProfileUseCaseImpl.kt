package com.innoprog.android.feature.profile.profiledetails.domain.impl

import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(private val repository: ProfileInfoRepo) :
    GetProfileUseCase {

    override fun getProfile(): Flow<Resource<Profile>> {
        return repository.loadProfile()
    }
}
