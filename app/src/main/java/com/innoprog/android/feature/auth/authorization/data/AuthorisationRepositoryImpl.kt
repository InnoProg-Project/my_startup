package com.innoprog.android.feature.auth.authorization.data

import android.util.Log
import com.innoprog.android.BuildConfig
import com.innoprog.android.feature.auth.authorization.data.network.LoginApi
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.network.data.ApiConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthorisationRepositoryImpl @Inject constructor(
    private val api: LoginApi
) : AuthorisationRepository {

    override fun verify(): Flow<AuthState> = flow {
        try {
            val response = api.authorize()
            when (response.code()) {
                ApiConstants.SUCCESS_CODE -> {
                    response.body()?.let { emit(AuthState.Success(it)) }
                        ?: emit(AuthState.GetProfileError)
                }

                ApiConstants.BAD_REQUEST -> emit(AuthState.VerificationError)
                else -> emit(AuthState.VerificationError)
            }
        } catch (e: HttpException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, " error: $e")
            }
            emit(AuthState.ConnectionError)
        } catch (e: SocketTimeoutException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, " error: $e")
            }
            emit(AuthState.ConnectionError)
        }
    }

    private companion object {
        val TAG = AuthorisationRepository::class.simpleName
    }
}
