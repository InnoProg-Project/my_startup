package com.innoprog.android.feature.auth.registration.presentation

import android.annotation.SuppressLint
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
    @SuppressLint("StaticFieldLeak") private val context: Context
) : BaseViewModel() {

    private var input: RegistrationModel? = null

    private val stateLiveData = MutableLiveData<RegistrationState>(RegistrationState.Default())
    fun observeState(): LiveData<RegistrationState> = stateLiveData
    private fun verify(login: String?, phone: String?, email: String?, password: String?): Boolean {
        return if (!(login.isNullOrEmpty() || password.isNullOrEmpty())) {
            if (!email.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            ) {
                val registrationPhone = if (phone.isNullOrEmpty()) null else phone
                input = RegistrationModel(login, registrationPhone, email, password, null, null)
                true
            } else {
                processResult(
                    RegistrationState.InputError(
                        getString(
                            context, R.string.registration_toast_message
                        ), RegistrationModel(login, phone, null, password, null, null)
                    )
                )
                false
            }
        } else {
            processResult(
                RegistrationState.InputError(
                    getString(
                        context, R.string.registration_toast_message
                    ), RegistrationModel(login, phone, email, password, null, null)
                )
            )
            false
        }
    }

    fun registration(login: String?, email: String?, phone: String?, password: String?) {
        if (verify(login, phone, email, password)) {
            viewModelScope.launch {
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
}
