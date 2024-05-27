package com.innoprog.android.feature.auth.registration.presentation

import android.os.Bundle
import android.text.InputType
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
import com.innoprog.android.feature.projects.projectdetails.presentation.TextChangedListener
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
        initPhoneInput()
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

    private  fun initPhoneInput(){
        binding.ivPhone.setInputType(InputType.TYPE_CLASS_PHONE)
        binding.ivPhone.addTextChangedListener(object : TextChangedListener {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setPhone(s.toString())
            }
        })
    }
    private fun initEmailInput() {
        with(binding.ivEmail) {
            setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            setSingleLine(true)
            addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyEmail(s.toString())
                }
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
            addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyUserName(s.toString())
                }
            })
        }
        viewModel.observeNameState().observe(viewLifecycleOwner) {
            setNameInputStatus(it)
        }
    }

    private fun initPasswordInput() {
        with(binding.ivPassword) {
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setSingleLine(true)
            setRightIconClickListener {
                isVisiblePassword = !isVisiblePassword
                renderIVPassword()
            }
            addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.verifyPassword(s.toString())
                }
            })
        }
        renderIVPassword()
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

    private fun setEmailInputStatus(state: InputState) {
        when (state) {
            InputState.DEFAULT -> {
                binding.ivEmail.setCaption("")
            }
            InputState.CORRECT -> {
                binding.ivEmail.renderState(InnoProgInputViewState.FOCUSED)
                binding.ivEmail.setCaption("")
            }
            InputState.ERROR -> {
                binding.ivEmail.renderState(InnoProgInputViewState.ERROR)
                binding.ivEmail.setCaption(getString(R.string.registration_email_error))
            }
        }
    }

    private fun setNameInputStatus(state: InputState) {
        when (state) {
            InputState.CORRECT, InputState.DEFAULT -> {
                binding.ivName.setCaption("")
            }
            InputState.ERROR -> {
                binding.ivName.renderState(InnoProgInputViewState.ERROR)
                binding.ivName.setCaption(getString(R.string.registration_name_error))
            }
        }
    }

    private fun setPasswordInputStatus(state: InputState) {
        when (state) {
            InputState.CORRECT, InputState.DEFAULT -> {
                binding.ivPassword.renderState(InnoProgInputViewState.INACTIVE)
                binding.ivPassword.setCaption("")
            }
            InputState.ERROR -> {
                binding.ivPassword.renderState(InnoProgInputViewState.ERROR)
                binding.ivPassword.setCaption(getString(R.string.registration_password_error))
            }
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
        if (state.registrationData.userName.isNullOrEmpty()) binding.ivName.renderState(
            InnoProgInputViewState.ERROR
        )
        if (state.registrationData.email.isNullOrEmpty()) binding.ivEmail.renderState(
            InnoProgInputViewState.ERROR
        )
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
