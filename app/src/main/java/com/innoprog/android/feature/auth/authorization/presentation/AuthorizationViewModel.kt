package com.innoprog.android.feature.auth.authorization.presentation

import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(private val useCase: AuthorisationUseCase) :
    BaseViewModel() {
    fun verify(inputLogin: String, inputPassword: String) {
        useCase.verify(inputLogin, inputPassword)
    }
}
