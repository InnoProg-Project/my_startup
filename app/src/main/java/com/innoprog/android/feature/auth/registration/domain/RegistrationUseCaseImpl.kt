package com.innoprog.android.feature.auth.registration.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCaseImpl @Inject constructor(private val repository: RegistrationRepository) :
    RegistrationUseCase {
    override fun registration(login: String, email: String, phone:String?, password: String): Flow<Pair<Boolean, String?>> {
        return repository.registration(login, email, phone, password)
    }
}
