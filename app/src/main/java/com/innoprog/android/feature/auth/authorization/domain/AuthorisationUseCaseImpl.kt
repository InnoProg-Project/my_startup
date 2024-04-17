package com.innoprog.android.feature.auth.authorization.domain

import com.innoprog.android.feature.auth.authorization.domain.model.UserData
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorisationUseCaseImpl @Inject constructor(private val repository: AuthorisationRepository) :
    AuthorisationUseCase {
    override fun verify(login: String, password: String): Flow<Resource<UserData>> {
        return repository.verify(login, password)
    }
}
