package com.innoprog.android.feature.profile.editingprofile.data.impl

import com.innoprog.android.feature.profile.common.ProfileCompanyResponse
import com.innoprog.android.feature.profile.common.ProfileResponse
import com.innoprog.android.feature.profile.editingprofile.data.network.EditingProfileApi
import com.innoprog.android.feature.profile.editingprofile.data.network.EditingProfileBody
import com.innoprog.android.feature.profile.editingprofile.data.network.EditingProfileCompanyBody
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditProfileInfoRepoImpl @Inject constructor(
    private val network: EditingProfileApi
) : EditProfileInfoRepo {

    override suspend fun editProfile(name: String, about: String): Flow<Resource<Profile>> = flow {

        val editingProfileBody = EditingProfileBody(name, about)
        val response = network.editProfile(editingProfileBody)

        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response) {
                    val result = mapToProfile(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    override suspend fun editProfileCompany(name: String, url: String, role: String): Flow<Resource<ProfileCompany>> = flow {
        val editingProfileBody = EditingProfileCompanyBody(name, url, role)
        val response = network.editProfileCompany(editingProfileBody)

        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response) {
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
            response.authorities
        )
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
