package com.innoprog.android.feature.auth.authorization.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import com.innoprog.android.feature.auth.authorization.domain.model.UserData
import com.innoprog.android.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(private val useCase: AuthorisationUseCase) :
    BaseViewModel() {

    private val stateLiveData = MutableLiveData<Pair<UserData?, String?>>()
    fun observeState(): LiveData<Pair<UserData?, String?>> = stateLiveData
    fun verify(inputLogin: String, inputPassword: String) {
        if (!inputLogin.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(inputLogin)
                .matches() && !inputPassword.isNullOrEmpty()
        ) {
            viewModelScope.launch {
                useCase.verify(inputLogin, inputPassword).collect {
                    when (it) {
                        is Resource.Success -> stateLiveData.postValue(Pair(it.data, null))
                        is Resource.Error -> stateLiveData.postValue(Pair(null, it.message))
                    }
                }
            }
        } else stateLiveData.postValue(Pair(null, "не все поля заполнены"))
    }
}
