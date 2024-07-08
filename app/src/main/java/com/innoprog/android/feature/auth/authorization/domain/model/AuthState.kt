package com.innoprog.android.feature.auth.authorization.domain.model

enum class AuthState {
    SUCCESS, CONNECTION_ERROR, VERIFICATION_ERROR, INPUT_ERROR, LOADING, EMPTY_SAVED_AUTH_DATA
}
