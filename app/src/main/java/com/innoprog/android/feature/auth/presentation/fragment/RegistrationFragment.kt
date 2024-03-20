package com.innoprog.android.feature.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentRegistrationBinding
import com.innoprog.android.di.DaggerAppComponent
import com.innoprog.android.feature.auth.presentation.viewmodel.RegistrationViewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<RegistrationViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@RegistrationFragment)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateUp()
        }

        binding.btnRegistration.setOnClickListener {
            viewModel.navigateTo(R.id.codeEntryFragment)
        }
    }
}
