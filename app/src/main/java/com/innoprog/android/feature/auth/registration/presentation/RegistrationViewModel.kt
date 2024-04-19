package com.innoprog.android.feature.auth.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.registration.domain.RegistrationUseCase
import com.innoprog.android.feature.auth.registration.domain.model.RegistrationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase
) : BaseViewModel() {

    private var input: RegistrationModel? = null

    private val stateLiveData = MutableLiveData<RegistrationState>(RegistrationState.Default())
    fun observeState(): LiveData<RegistrationState> = stateLiveData
    private fun verify(login: String?, phone: String?, email: String?, password: String?): Boolean {
        return if (!(login.isNullOrEmpty() || password.isNullOrEmpty())) {
            if (!email.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            ) {
                input = RegistrationModel(login, phone, email, password)
                true
            } else {
                processResult(
                    RegistrationState.InputError(
                        "",
                        RegistrationModel(login, phone, null, password)
                    )
                )
                false
            }
        } else false
    }

    fun registration(login: String?, email: String?, phone: String?, password: String?) {
        if (verify(login, phone, email, password)) {
            viewModelScope.launch {
                input?.let {
                    useCase
                        .registration(it)
                        .collect { pair ->
                            if (pair.first) processResult(RegistrationState.InputComplete(it)) else processResult(
                                RegistrationState.VerificationError(pair.second ?: "")
                            )
                        }
                }
            }
        }
    }

    private fun processResult(result: RegistrationState) {
        stateLiveData.postValue(result)
    }

    fun clearDate() {
        stateLiveData.postValue(RegistrationState.Default())
    }
}
