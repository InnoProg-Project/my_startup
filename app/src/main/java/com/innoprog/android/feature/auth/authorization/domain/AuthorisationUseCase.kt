package com.innoprog.android.feature.auth.authorization.domain

interface AuthorisationUseCase {
    fun verify(login: String, password: String)
}
