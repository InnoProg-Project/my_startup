package com.innoprog.android.feature.auth.authorization.domain

import com.innoprog.android.feature.auth.authorization.data.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthorisationRepository {
    fun verify(login: String, password: String): Flow<LoginResponse>
}
