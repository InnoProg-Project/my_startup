package com.innoprog.android.feature.auth.registration.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.registrationTitle.setLeftIconClickListener{
            viewModel.navigateUp()
        }

        binding.bvRegistration.setOnClickListener {
            viewModel.navigateTo(R.id.codeEntryFragment)
        }
    }
}
