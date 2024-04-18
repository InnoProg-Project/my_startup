package com.innoprog.android.feature.auth.registration.domain

import com.innoprog.android.feature.auth.registration.domain.Model.RegistrationModel
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun registration(registrationValue: RegistrationModel): Flow<Pair<Boolean, String?>>
}
