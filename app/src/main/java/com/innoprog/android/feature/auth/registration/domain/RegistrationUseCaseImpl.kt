package com.innoprog.android.feature.auth.registration.domain

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCaseImpl @Inject constructor(private val repository: RegistrationRepository) :
    RegistrationUseCase {
    override fun registration(
        registrationValue: RegistrationModel
    ): Flow<Pair<Boolean, String?>> {
        return repository.registration(registrationValue)
    }
}
