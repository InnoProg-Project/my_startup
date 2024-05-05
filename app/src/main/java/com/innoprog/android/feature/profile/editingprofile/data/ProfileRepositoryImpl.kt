package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.feature.profile.editingprofile.domain.model.CompanyBody
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : ProfileRepository {

    override suspend fun editProfile(): Flow<Resource<Body>> = flow {
        val response = networkClient.editProfile()
        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response as BodyResponse) {
                    val result = mapToBody(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    override suspend fun editProfileCompany(): Flow<Resource<CompanyBody>> = flow {
        val response = networkClient.editProfileCompany()
        when (response.resultCode) {
            ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response as CompanyBodyResponse) {
                    val result = mapToCompanyBody(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error(ErrorType.BAD_REQUEST))
            }
        }
    }

    private fun mapToBody(response: BodyResponse): Body {
        return Body(
            about = response.about,
            name = response.name
        )
    }

    private fun mapToCompanyBody(response: CompanyBodyResponse): CompanyBody {
        return CompanyBody(
            name = response.name,
            role = response.role,
            url = response.url

        )
    }
}
