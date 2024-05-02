package com.innoprog.android.feature.auth.registration.presentation

import android.os.Bundle
import android.text.InputType
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
        binding.ivEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        binding.ivPhone.setInputType(InputType.TYPE_CLASS_PHONE)
        binding.ivPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.registrationTitle.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        binding.bvRegistration.setOnClickListener {
            viewModel.registration(
                binding.ivName.getText(),
                binding.ivEmail.getText(),
                binding.ivPhone.getText(),
                binding.ivPassword.getText()
            )
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
