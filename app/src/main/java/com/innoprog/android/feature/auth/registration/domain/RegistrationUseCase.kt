package com.innoprog.android.feature.auth.registration.domain

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.flow.Flow

interface RegistrationUseCase {
    fun registration(registrationValue: RegistrationModel): Flow<Pair<Boolean, String?>>
}
