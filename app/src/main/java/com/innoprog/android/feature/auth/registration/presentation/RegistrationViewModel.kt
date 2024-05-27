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


    private val emailStateLiveData = MutableLiveData<Boolean>(true)
    fun observeEmailState(): LiveData<Boolean> = emailStateLiveData

    private val nameStateLiveData = MutableLiveData<Boolean>(true)
    fun observeNameState(): LiveData<Boolean> = nameStateLiveData

    private val passwordStateLiveData = MutableLiveData<Boolean>(true)
    fun observePasswordState(): LiveData<Boolean> = passwordStateLiveData

    private val stateLiveData = MutableLiveData<RegistrationState>(RegistrationState.Default())
    fun observeState(): LiveData<RegistrationState> = stateLiveData

    fun verifyUserName(userName: String) {
        if (userName.length in MIN_NAME..MAX_NAME) {
            nameStateLiveData.postValue(true)
            this.email = null
        } else {
            nameStateLiveData.postValue(false)
            this.userName = userName
        }
    }

    fun verifyEmail(email: String) {
        if (email.length in MIN_EMAIL..MAX_EMAIL && android.util.Patterns.EMAIL_ADDRESS.matcher(
                email
            )
                .matches()
        ) {
            emailStateLiveData.postValue(true)
            this.email = null
        } else {
            emailStateLiveData.postValue(false)
            this.email = email
        }
    }

    fun verifyPassword(password: String) {
        if (password.length in MIN_PASSWORD..MAX_PASSWORD) {
            passwordStateLiveData.postValue(true)
            this.password = null
        } else {
            passwordStateLiveData.postValue(false)
            this.password = password
        }
    }

    private fun verify(): Boolean {
        return if (!(userName.isNullOrEmpty() || password.isNullOrEmpty())) {
            if (!email.isNullOrEmpty()
            ) {
                val registrationPhone = if (phone.isNullOrEmpty()) null else phone
                input = RegistrationModel(userName, registrationPhone, email, password)
                true
            } else {
                processResult(
                    RegistrationState.InputError(
                        getString(
                            context, R.string.registration_toast_message
                        ), RegistrationModel(userName, phone, null, password)
                    )
                )
                false
            }
        } else {
            processResult(
                RegistrationState.InputError(
                    getString(
                        context, R.string.registration_toast_message
                    ), RegistrationModel(userName, phone, email, password)
                )
            )
            false
        }
    }

    fun registration() {
        if (verify()) {
            viewModelScope.launch() {
                input?.let {
                    useCase.registration(it).collect { pair ->
                        if (pair.first) processResult(RegistrationState.InputComplete(it)) else processResult(
                            RegistrationState.VerificationError(
                                pair.second ?: getString(
                                    context, R.string.registration_error
                                )
                            )
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

    companion object {
        const val MIN_NAME = 2
        const val MAX_NAME = 60
        const val MIN_EMAIL = 3
        const val MAX_EMAIL = 255
        const val MIN_PASSWORD = 6
        const val MAX_PASSWORD = 60
    }
}
