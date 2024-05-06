package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileApi
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileCompanyResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileResponse
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileInfoRepoImpl @Inject constructor(
    private val network: ProfileApi
) : ProfileInfoRepo {

    override fun loadProfile(): Flow<Resource<Profile>> = flow {

        val response = network.loadProfile()
        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response as ProfileResponse) {
                    val result = mapToProfile(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    override fun loadProfileCompany(): Flow<Resource<ProfileCompany>> = flow {
        val response = network.loadProfileCompany()
        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response as ProfileCompanyResponse) {
                    val result = mapToProfileCompany(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    private fun mapToProfile(response: ProfileResponse): Profile {
        return Profile(
            response.userId,
            response.name,
            response.about,
            response.communicationChannels,
            response.authorities)
    }

    private fun mapToProfileCompany(response: ProfileCompanyResponse): ProfileCompany {
        return ProfileCompany(
            response.id,
            response.userId,
            response.name,
            response.url,
            response.role
        )
    }
}
