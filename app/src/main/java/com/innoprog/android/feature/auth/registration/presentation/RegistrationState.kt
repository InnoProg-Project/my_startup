package com.innoprog.android.feature.auth.registration.presentation

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel

sealed class RegistrationState {
    data object Default : RegistrationState()
    data object InputComplete : RegistrationState()
    class InputError(val registrationData: RegistrationModel) : RegistrationState()
    class VerificationError(val message: String) : RegistrationState()
}
