package com.innoprog.android.feature.auth.authorization.domain

import javax.inject.Inject

class AuthorisationUseCaseImpl @Inject constructor(private val repository: AuthorisationRepository) :
    AuthorisationUseCase {
    override fun verify(login: String, password: String) {
        repository.verify(login, password)
    }
}
