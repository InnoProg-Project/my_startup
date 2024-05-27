package com.innoprog.android.feature.auth.registration.presentation

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innoprog.android.R
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.feature.auth.registration.domain.RegistrationUseCase
import com.innoprog.android.feature.auth.registration.domain.models.RegistrationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUseCase,
    private val context: Context
) : BaseViewModel() {

    private var input: RegistrationModel? = null
    private var userName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var phone: String? = null

    private val emailStateLiveData = MutableLiveData(InputState.DEFAULT)
    fun observeEmailState(): LiveData<InputState> = emailStateLiveData

    private val nameStateLiveData = MutableLiveData(InputState.DEFAULT)
    fun observeNameState(): LiveData<InputState> = nameStateLiveData

    private val passwordStateLiveData = MutableLiveData(InputState.DEFAULT)
    fun observePasswordState(): LiveData<InputState> = passwordStateLiveData

    private val stateLiveData = MutableLiveData<RegistrationState>(RegistrationState.Default())
    fun observeState(): LiveData<RegistrationState> = stateLiveData

    fun verifyUserName(userName: String) {
        if (userName.length in MIN_NAME..MAX_NAME) {
            nameStateLiveData.postValue(InputState.CORRECT)
            this.userName = userName
        } else {
            nameStateLiveData.postValue(InputState.ERROR)
            this.userName = null
        }
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun verifyEmail(email: String) {
        if (email.length in MIN_EMAIL..MAX_EMAIL && android.util.Patterns.EMAIL_ADDRESS.matcher(
                email
            )
                .matches()
        ) {
            emailStateLiveData.postValue(InputState.CORRECT)
            this.email = email
        } else {
            emailStateLiveData.postValue(InputState.ERROR)
            this.email = null
        }
    }

    fun verifyPassword(password: String) {
        if (password.length in MIN_PASSWORD..MAX_PASSWORD) {
            passwordStateLiveData.postValue(InputState.CORRECT)
            this.password = password
        } else {
            if (password.isEmpty()) passwordStateLiveData.postValue(InputState.DEFAULT) else {
                passwordStateLiveData.postValue(InputState.ERROR)
                this.password = null
            }
        }
    }

    private fun verify(): Boolean {
        return if ((userName.isNullOrEmpty() || password.isNullOrEmpty()) || email.isNullOrEmpty()) {
            processResult(
                RegistrationState.InputError(
                    getString(
                        context, R.string.registration_toast_message
                    ), RegistrationModel(userName, phone, email, password)
                )
            )
            false
        } else {
            val registrationPhone = if (phone.isNullOrEmpty()) null else phone
            input = RegistrationModel(userName, registrationPhone, email, password)
            true
        }
    }

    fun registration() {
        if (verify()) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    useCase.registration(input!!).collect { pair ->
                        if (pair.first) processResult(RegistrationState.InputComplete(input!!)) else processResult(
                            RegistrationState.VerificationError(
                                pair.second ?: getString(
                                    context, R.string.registration_error
                                )
                            )
                        )
                    }
                }.onFailure {
                    processResult(RegistrationState.VerificationError("dataError"))
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

    companion object {
        const val MIN_NAME = 2
        const val MAX_NAME = 60
        const val MIN_EMAIL = 3
        const val MAX_EMAIL = 255
        const val MIN_PASSWORD = 6
        const val MAX_PASSWORD = 60
    }
}
