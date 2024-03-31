package com.innoprog.android.feature.auth.authorization.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(private val useCase: AuthorisationUseCase) :
    BaseViewModel() {
    private var inputLogin: String? = null
    private var inputPassword: String? = null
    fun verify() {
        useCase.verify(inputLogin ?: "", inputPassword ?: "")
    }
}
