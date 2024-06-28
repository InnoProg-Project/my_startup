package com.innoprog.android.feature.profile.profiledetails.data.impl

import com.innoprog.android.db.RoomDB
import com.innoprog.android.feature.profile.common.ProfileCompanyResponse
import com.innoprog.android.feature.profile.common.ProfileResponse
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileCompanyEntity
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileEntity
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileApi
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
    private val network: ProfileApi,
    private val roomDB: RoomDB
) : ProfileInfoRepo {

    override suspend fun loadProfile(): Flow<Resource<Profile>> = flow {
        val response = network.loadProfile()
        roomDB.profileDao().saveProfile(
            ProfileEntity(
                userId = response.userId,
                name = response.name,
                about = response.about,
                communicationChannels = response.communicationChannels,
                authorities = response.authorities
            )
        )
        when (response.resultCode) {
            ApiConstants.NO_CONNECTION -> {
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

    override suspend fun loadProfileCompany(): Flow<Resource<ProfileCompany>> = flow {
        val response = network.loadProfileCompany()
        roomDB.profileCompanyDao().saveProfileCompany(
            ProfileCompanyEntity(
                id = response.id,
                userId = response.userId,
                name = response.name,
                url = response.url,
                role = response.role
            )
        )
        when (response.resultCode) {
            ApiConstants.NO_CONNECTION -> {
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
