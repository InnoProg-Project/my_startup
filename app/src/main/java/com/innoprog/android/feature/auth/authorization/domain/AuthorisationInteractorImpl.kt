package com.innoprog.android.feature.auth.authorization.domain

import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.network.domain.AuthorizationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthorisationInteractorImpl @Inject constructor(
    private val repository: AuthorisationRepository,
    private val dataRepository: AuthorizationDataRepository
) : AuthorisationInteractor {
    override fun verify(login: String, password: String): Flow<AuthState> {
        dataRepository.setData(login, password)
        return repository.verify()
    }

    override fun verifyOnStart(): Flow<AuthState> {
        dataRepository.checkLastLoginTime()
        val credentials = dataRepository.loadCredentials()
            ?: return flowOf(AuthState.EmptyLocalAuthData)
        val (username, password) = credentials
        return verify(username, password)
    }
}
