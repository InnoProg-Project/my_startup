package com.innoprog.android.feature.auth.authorization.data

import android.util.Log
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.model.UserData
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
            ERROR -> {
                emit(Resource.Error("Ошибка сервера"))
            }

            SUCCESS -> {
                with(response as LoginResponse) {
                    Log.d("loginResponse", "Response: $response")
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

    companion object {
        const val ERROR = -1
        const val SUCCESS = 200
    }
}
