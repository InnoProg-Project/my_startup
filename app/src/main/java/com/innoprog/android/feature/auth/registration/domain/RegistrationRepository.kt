package com.innoprog.android.feature.auth.registration.domain

interface RegistrationRepository {
    fun verify(login: String, password: String)
}
