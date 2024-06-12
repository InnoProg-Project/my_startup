package com.innoprog.android.feature.auth.authorization.data

import android.util.Log
import com.innoprog.android.feature.auth.authorization.data.network.LoginApi
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.local.LocalStorage
import com.innoprog.android.network.data.ApiConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Base64
import javax.inject.Inject

class AuthorisationRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val local: LocalStorage,
) :
    AuthorisationRepository {

    override fun verify(login: String, password: String): Flow<AuthState> = flow {
        try {
            val authData = "$login:$password"
            val header = "Basic" + Base64.getEncoder().encodeToString(authData.toByteArray())
            val response = api.authorize(header)
            Log.d("123", response.code.toString())
            when (response.code) {
                SUCCESS -> emit(AuthState.SUCCESS)
                ERROR -> emit(AuthState.VERIFICATION_ERROR)
                else -> emit(AuthState.VERIFICATION_ERROR)
            }
        } catch (e: Exception) {
            Log.e("RetrofitNetworkClient", "An error occurred", e)
            emit(AuthState.CONNECTION_ERROR)
        }
    }

    companion object {
        const val SUCCESS = 200
        const val ERROR = 400
    }
}
