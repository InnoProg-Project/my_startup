package com.innoprog.android.feature.auth.authorization.domain.model

import com.innoprog.android.feature.auth.authorization.data.network.LoginResponse

sealed interface AuthState {
    class Success(val loginResponse: LoginResponse) : AuthState
    data object ConnectionError : AuthState
    data object VerificationError : AuthState
    data object InputError : AuthState
    data object GetProfileError : AuthState
    data object Loading : AuthState
    data object EmptyLocalAuthData : AuthState
}
