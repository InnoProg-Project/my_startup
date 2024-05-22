package com.innoprog.android.feature.auth.registration.domain

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel

interface RegistrationRepository {
    suspend fun registration(registrationValue: RegistrationModel): Pair<Boolean, String?>
}
