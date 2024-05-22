package com.innoprog.android.feature.auth.authorization.data

import android.util.Log
import com.innoprog.android.feature.auth.authorization.data.network.AuthorizationBody
import com.innoprog.android.feature.auth.authorization.data.network.LoginApi
import com.innoprog.android.feature.auth.authorization.data.network.LoginResponse
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.model.UserData
import com.innoprog.android.local.LocalStorage
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorisationRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val local: LocalStorage,
) :
    AuthorisationRepository {

    override fun verify(login: String, password: String): Flow<Resource<UserData>> = flow {
        try {
            val response = api.authorize(AuthorizationBody(login, password))
            Log.d("response", this.toString())
            when (response.resultCode) {
                ApiConstants.BAD_REQUEST_CODE -> {
                    emit(
                        Resource.Error(
                            ErrorType.BAD_REQUEST
                        )
                    )
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as LoginResponse) {
                        val result = mapToUserDate(this)
                        local.headers = response.headers
                        emit(Resource.Success(result))
                    }
                }

                else -> {
                    emit(
                        Resource.Error(
                            ErrorType.BAD_REQUEST
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("RetrofitNetworkClient", "An error occurred", e)
            Response().apply { resultCode = ApiConstants.BAD_REQUEST_CODE }
        }
    }

    private fun mapToUserDate(response: LoginResponse): UserData {
        return UserData(response.id, response.authorities)
    }
}
