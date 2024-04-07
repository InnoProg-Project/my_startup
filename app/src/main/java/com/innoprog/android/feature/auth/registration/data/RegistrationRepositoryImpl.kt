package com.innoprog.android.feature.auth.registration.data

import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor() : RegistrationRepository {
    override fun verify(login: String, password: String) {

    }
}