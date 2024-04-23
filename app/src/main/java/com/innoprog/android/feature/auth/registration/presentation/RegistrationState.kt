package com.innoprog.android.feature.auth.registration.presentation

import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel

sealed class RegistrationState(val message: String?) {
    class Default : RegistrationState(null)
    class InputError(message: String, val registrationData: RegistrationModel) :
        RegistrationState(message)

    class VerificationError(message: String) : RegistrationState(message)
    class InputComplete(val registrationData: RegistrationModel) : RegistrationState(null)
}
