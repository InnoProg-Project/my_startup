package com.innoprog.android.feature.auth.authorization.domain.model

import com.innoprog.android.feature.auth.authorization.data.network.LoginResponse

sealed interface AuthState {
    data class Success(val loginResponse: LoginResponse) : AuthState
    data object CONNECTION_ERROR : AuthState
    data object VERIFICATION_ERROR : AuthState
    data object INPUT_ERROR : AuthState
    data object GET_PROFILE_ERROR : AuthState
}
