package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.db.RoomDB
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileCompanyEntity
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileEntity
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileCompanyResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileResponse
import com.innoprog.android.feature.profile.profiledetails.data.network.RequestByProfile
import com.innoprog.android.feature.profile.profiledetails.data.network.mapToDomainCompany
import com.innoprog.android.feature.profile.profiledetails.data.network.mapToDomainUserData
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.NetworkClient
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileInfoRepoImpl @Inject constructor(
    private val network: NetworkClient,
    private val roomDB: RoomDB
) : ProfileInfoRepo {

    override suspend fun loadProfile(): Flow<Resource<Profile>> = flow {
        val response = network.doRequest(RequestByProfile.GetProfile)
        if (response is ProfileResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            emit(Resource.Success(response.mapToDomainUserData()))
            roomDB.profileDao().saveProfile(
                ProfileEntity(
                    userId = response.userId,
                    name = response.name,
                    about = response.about,
                    communicationChannels = response.communicationChannels,
                    authorities = response.authorities
                )
            )
        } else {
            emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    override suspend fun loadProfileCompany(): Flow<Resource<ProfileCompany>> = flow {
        val response = network.doRequest(RequestByProfile.GetProfileCompany)
        if (response is ProfileCompanyResponse && response.resultCode == ApiConstants.SUCCESS_CODE) {
            emit(Resource.Success(response.mapToDomainCompany()))
            roomDB.profileCompanyDao().saveProfileCompany(
                ProfileCompanyEntity(
                    id = response.id,
                    userId = response.userId,
                    name = response.name,
                    url = response.url,
                    role = response.role
                )
            )
        } else {
            emit(Resource.Error(getErrorType(response.resultCode)))
        }
    }

    private fun getErrorType(code: Int): ErrorType = when (code) {
        ApiConstants.NO_INTERNET_CONNECTION_CODE -> ErrorType.NO_CONNECTION
        ApiConstants.BAD_REQUEST_CODE -> ErrorType.BAD_REQUEST
        ApiConstants.FORBIDDEN -> ErrorType.CAPTCHA_REQUIRED
        ApiConstants.NOT_FOUND -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }
}
