package com.innoprog.android.feature.auth.authorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(private val useCase: AuthorisationUseCase) :
    BaseViewModel() {

    private val stateLiveData = MutableLiveData<AuthState>()
    fun observeState(): LiveData<AuthState> = stateLiveData
    fun verify(inputLogin: String, inputPassword: String) {
        if (inputLogin.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(inputLogin)
                .matches() && inputPassword.isNotEmpty()
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    useCase.verify(inputLogin, inputPassword).collect {
                        stateLiveData.postValue(it)
                    }
                }.onFailure {
                    stateLiveData.postValue(AuthState.CONNECTION_ERROR)
                }
            }
        } else {
            stateLiveData.postValue(AuthState.INPUT_ERROR)
        }
    }
}
