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
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.registration.di.DaggerRegistrationComponent

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<RegistrationViewModel>()
    override fun diComponent(): ScreenComponent = DaggerRegistrationComponent.builder().build()

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
            render(it.first, it.second)
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

    private fun render(isAccepted: Boolean, message: String?) {
        if (isAccepted) viewModel.navigateTo(R.id.codeEntryFragment) else Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}
