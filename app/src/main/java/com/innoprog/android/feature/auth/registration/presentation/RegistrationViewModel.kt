package com.innoprog.android.feature.auth.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.registration.domain.RegistrationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val useCase: RegistrationUseCase) :
    BaseViewModel() {

    private val stateLiveData = MutableLiveData<Pair<Boolean, String?>>()
    fun observeState(): LiveData<Pair<Boolean, String?>> = stateLiveData
    private fun verify(login: String?, email: String?, password: String?): Boolean {
        return !(login.isNullOrEmpty() || password.isNullOrEmpty() || email.isNullOrEmpty())
    }

    fun registration(login: String?, email: String?, phone: String?, password: String?) {
        if (verify(login, email, password)) {
            viewModelScope.launch {
                useCase
                    .registration(login ?: "", email ?: "", phone, password ?: "")
                    .collect { pair ->
                        processResult(pair)
                    }
            }
        } else processResult(Pair(false, "не все поля заполнены"))
    }

    private fun processResult(result: Pair<Boolean, String?>) {
        stateLiveData.postValue(result)
    }

    fun clearDate(){
        stateLiveData.postValue(Pair(false,null))
    }
}
