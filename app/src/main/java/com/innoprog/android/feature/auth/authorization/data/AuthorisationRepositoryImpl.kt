package com.innoprog.android.feature.auth.authorization.data

import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.training.data.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorisationRepositoryImpl @Inject constructor() : AuthorisationRepository {
    override fun verify(login: String, password: String): Flow<LoginResponse> = flow {
        val response = LoginResponse("3fa85f64-5717-4562-b3fc-2c963f66afa6", "string", listOf("string"))
        emit(response)
    }
}
