package com.innoprog.android.feature.auth.registration.domain.models

data class RegistrationModel(
    val userName: String?,
    val phone: String?,
    val email: String?,
    val password: String?,
    val about: String?,
    val language: String?
)
