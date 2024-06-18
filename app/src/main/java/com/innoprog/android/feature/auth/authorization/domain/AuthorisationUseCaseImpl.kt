package com.innoprog.android.feature.auth.authorization.domain

import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.network.domain.AuthorizationDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorisationUseCaseImpl @Inject constructor(
    private val repository: AuthorisationRepository,
    private val dataRepository: AuthorizationDataRepository
) :
    AuthorisationUseCase {
    override fun verify(login: String, password: String): Flow<AuthState> {
        dataRepository.setData(login, password)
        return repository.verify()
    }
}
