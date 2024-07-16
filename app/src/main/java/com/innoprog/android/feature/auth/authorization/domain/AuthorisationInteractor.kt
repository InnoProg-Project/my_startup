package com.innoprog.android.feature.auth.authorization.domain

import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthorisationInteractor {
    fun verify(login: String, password: String): Flow<AuthState>

    fun verifyOnStart(): Flow<AuthState>
}
