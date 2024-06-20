package com.innoprog.android.feature.profile.editingprofile.data.impl

import com.innoprog.android.db.RoomDB
import com.innoprog.android.feature.profile.common.ProfileCompanyResponse
import com.innoprog.android.feature.profile.common.ProfileResponse
import com.innoprog.android.feature.profile.editingprofile.data.network.Request
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileCompanyEntity
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileEntity
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditProfileInfoRepoImpl @Inject constructor(
    private val network: NetworkClient,
    private val roomDB: RoomDB

) : EditProfileInfoRepo {

    override suspend fun editProfile(name: String, about: String): Flow<Resource<Profile>> = flow {

        val apiResponse =
            network.doRequest(Request.PutProfile)

        if (apiResponse is ProfileResponse && apiResponse.resultCode == ApiConstants.SUCCESS_CODE) {
            emit(Resource.Success(mapToProfile(apiResponse)))
            roomDB.profileDao().saveProfile(
                ProfileEntity(
                    userId = apiResponse.userId,
                    name = apiResponse.name,
                    about = apiResponse.about,
                    communicationChannels = apiResponse.communicationChannels,
                    authorities = apiResponse.authorities
                )
            )
        } else
            emit(Resource.Error(getErrorType(apiResponse.resultCode)))
    }

    override suspend fun editProfileCompany(
        name: String,
        url: String,
        role: String
    ): Flow<Resource<ProfileCompany>> = flow {
        val response = network.doRequest(Request.PutProfileCompany)
        if (response is ProfileCompanyResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            emit(Resource.Success(mapToProfileCompany(response)))
            roomDB.profileCompanyDao().saveProfileCompany(
                ProfileCompanyEntity(
                    id = response.id,
                    userId = response.userId,
                    name = response.name,
                    url = response.url,
                    role = response.role
                )
            )
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
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

    private fun getErrorType(code: Int): ErrorType = when (code) {
        ApiConstants.NO_CONNECTION -> ErrorType.NO_CONNECTION
        ApiConstants.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        ApiConstants.CAPTCHA_REQUIRED -> ErrorType.CAPTCHA_REQUIRED
        ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }
}
