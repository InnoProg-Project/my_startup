package com.innoprog.android.feature.auth.registration.domain

interface RegistrationUseCase {
    fun verify(login: String, password: String)
}
