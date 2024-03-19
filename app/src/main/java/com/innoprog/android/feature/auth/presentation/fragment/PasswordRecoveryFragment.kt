package com.innoprog.android.feature.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentPasswordRecoveryBinding
import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.DaggerAppComponent
import com.innoprog.android.feature.auth.presentation.viewmodel.PasswordRecoveryViewModel

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<PasswordRecoveryViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordRecoveryBinding {
        return FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@PasswordRecoveryFragment)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateUp()
        }

        binding.btnSendCode.setOnClickListener {
            viewModel.navigateTo(R.id.codeEntryFragment)
        }
    }
}
