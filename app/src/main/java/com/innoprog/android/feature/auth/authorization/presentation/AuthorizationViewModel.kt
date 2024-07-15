package com.innoprog.android.feature.auth.authorization.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val useCase: AuthorisationUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel() {
    private val stateLiveData = MutableLiveData<AuthState>()
    fun observeState(): LiveData<AuthState> = stateLiveData

    fun verify(inputLogin: String, inputPassword: String) {
        if (inputLogin.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(inputLogin)
                .matches() && inputPassword.isNotEmpty()
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    useCase.verify(inputLogin, inputPassword).collect {
                        if (it is AuthState.Success) {
                            runCatching {
                                getProfileUseCase.getProfile(it.loginResponse.userId)
                                    .collect { getProfileResult ->
                                        stateLiveData.postValue(it)
                                    }
                            }.onFailure { throwable ->
                                Log.d("throw_profile", throwable.toString())
                                stateLiveData.postValue(AuthState.GetProfileError)
                            }
                        } else {
                            stateLiveData.postValue(it)
                        }
                    }
                }.onFailure {
                    stateLiveData.postValue(AuthState.ConnectionError)
                }
            }
        } else {
            stateLiveData.postValue(AuthState.InputError)
        }
    }
}
