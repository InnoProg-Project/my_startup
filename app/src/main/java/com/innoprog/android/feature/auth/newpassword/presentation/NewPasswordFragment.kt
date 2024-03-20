package com.innoprog.android.feature.auth.newpassword.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentNewPasswordBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.newpassword.di.DaggerNewPasswordComponent

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<NewPasswordViewModel>()
    override fun diComponent(): ScreenComponent = DaggerNewPasswordComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateTo(R.id.passwordRecoveryFragment)
        }
    }
}
