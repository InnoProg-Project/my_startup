package com.innoprog.android.feature.auth.authorization.data

import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.model.UserData
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorisationRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) :
    AuthorisationRepository {
    override fun verify(login: String, password: String): Flow<Resource<UserData>> = flow {
        val response = networkClient.authorize(AuthorizationBody(login, password))
        when (response.resultCode) {
            ApiConstants.BAD_REQUEST_CODE -> {
                emit(Resource.Error("Ошибка сервера"))
            }

            ApiConstants.SUCCESS_CODE -> {
                with(response as LoginResponse) {
                    val result = mapToUserDate(this)
                    emit(Resource.Success(result))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    private fun mapToUserDate(response: LoginResponse): UserData {
        return UserData(response.id, response.authorities)
    }
}
