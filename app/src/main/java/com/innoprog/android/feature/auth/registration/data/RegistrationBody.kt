package com.innoprog.android.feature.auth.registration.data

data class RegistrationBody(
    val name: String,
    val email: String,
    val phone: String?,
    val password: String,
    val about: String?,
    val language: String?
)
