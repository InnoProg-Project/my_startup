package com.innoprog.android.feature.auth.registration.presentation

import com.innoprog.android.feature.auth.registration.domain.Model.RegistrationModel

sealed class RegistrationState(val message: String?) {
    class Default : RegistrationState(null)
    class InputError(message: String, val model: RegistrationModel) : RegistrationState(message)
    class VerificationError(message: String) : RegistrationState(message)
    class InputComplete(val model: RegistrationModel) : RegistrationState(null)
}
