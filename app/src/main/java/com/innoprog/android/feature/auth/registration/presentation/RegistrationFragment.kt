package com.innoprog.android.feature.auth.registration.presentation

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentRegistrationBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.codeentry.presentation.CodeEntryFragment
import com.innoprog.android.feature.auth.registration.di.DaggerRegistrationComponent
import com.innoprog.android.uikit.InnoProgInputViewState

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<RegistrationViewModel>()
    private var isVisiblePassword = false

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerRegistrationComponent.builder().appComponent(appComponent).build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivPhone.setInputType(InputType.TYPE_CLASS_PHONE)
        initEmailInput()
        initNameInput()
        initPasswordInput()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.registrationTitle.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        binding.bvRegistration.setOnClickListener {
            viewModel.registration()
        }
    }

    private fun initEmailInput() {
        with(binding.ivEmail) {
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            setSingleLine(true)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyEmail(s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        viewModel.observeEmailState().observe(viewLifecycleOwner) {
            setEmailInputStatus(it)
        }
    }

    private fun initNameInput() {
        with(binding.ivName) {
            setInputType(InputType.TYPE_CLASS_TEXT)
            setSingleLine(true)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyUserName(s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        viewModel.observeNameState().observe(viewLifecycleOwner) {
            setNameInputStatus(it)
        }
    }

    private fun initPasswordInput() {
        renderIVPassword()
        with(binding.ivPassword) {
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setSingleLine(true)
            setRightIconClickListener {
                isVisiblePassword = !isVisiblePassword
                renderIVPassword()
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyPassword(s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        viewModel.observePasswordState().observe(viewLifecycleOwner) {
            setPasswordInputStatus(it)
        }
    }

    private fun renderIVPassword() {
        if (isVisiblePassword) {
            binding.ivPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            binding.ivPassword.setRightIcon(R.drawable.eye_off)
        } else {
            binding.ivPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
            binding.ivPassword.setRightIcon(R.drawable.eye)
        }
    }

    private fun setEmailInputStatus(isCorrect: Boolean) {
        if (isCorrect) {
            binding.ivEmail.renderState(InnoProgInputViewState.INACTIVE)
            binding.ivEmail.setCaption("")
        } else {
            binding.ivEmail.renderState(InnoProgInputViewState.ERROR)
            binding.ivEmail.setCaption(getString(R.string.registration_email_error))
        }
    }

    private fun setNameInputStatus(isCorrect: Boolean) {
        if (isCorrect) {
            binding.ivName.renderState(InnoProgInputViewState.INACTIVE)
            binding.ivName.setCaption("")
        } else {
            binding.ivName.renderState(InnoProgInputViewState.ERROR)
            binding.ivName.setCaption(getString(R.string.registration_name_error))
        }
    }

    private fun setPasswordInputStatus(isCorrect: Boolean) {
        if (isCorrect) {
            binding.ivPassword.renderState(InnoProgInputViewState.INACTIVE)
            binding.ivPassword.setCaption("")
        } else {
            binding.ivPassword.renderState(InnoProgInputViewState.ERROR)
            binding.ivPassword.setCaption(getString(R.string.registration_password_error))
        }
    }

    private fun forwardNavigate(state: RegistrationState.InputComplete) {
        val bundle = Bundle()
        bundle.putString(CodeEntryFragment.ARG, state.registrationData.email)
        viewModel.clearDate()
        viewModel.navigateTo(R.id.codeEntryFragment, bundle)
    }

    private fun render(state: RegistrationState) {
        when (state) {
            is RegistrationState.InputComplete -> forwardNavigate(state)
            is RegistrationState.InputError -> drawError(state)
            is RegistrationState.VerificationError -> showToast(state)
            else -> Unit
        }
    }

    private fun showToast(state: RegistrationState.VerificationError) {
        Toast.makeText(
            requireContext(),
            state.message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun drawError(state: RegistrationState.InputError) {
        if (state.registrationData.userName.isNullOrEmpty()) binding.ivName.renderState(InnoProgInputViewState.ERROR)
        if (state.registrationData.email.isNullOrEmpty()) binding.ivEmail.renderState(InnoProgInputViewState.ERROR)
        if (state.registrationData.password.isNullOrEmpty()) binding.ivPassword.renderState(
            InnoProgInputViewState.ERROR
        )
        Toast.makeText(
            requireContext(),
            state.message,
            Toast.LENGTH_LONG
        ).show()
    }
}
