package com.innoprog.android.feature.profile.profiledetails.domain.impl

import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileCompanyUseCaseImpl @Inject constructor(private val repository: ProfileInfoRepo) :
    GetProfileCompanyUseCase {

    override fun getProfileCompany(): Flow<Resource<ProfileCompany>> {
        return repository.loadProfileCompany()
    }
}
