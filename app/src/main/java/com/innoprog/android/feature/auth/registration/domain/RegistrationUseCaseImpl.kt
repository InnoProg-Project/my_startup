package com.innoprog.android.feature.auth.registration.domain

import javax.inject.Inject

class RegistrationUseCaseImpl @Inject constructor(private val repository: RegistrationRepository) :
    RegistrationUseCase {
    override fun verify(login: String, password: String) {
        repository.verify(login, password)
    }
}
