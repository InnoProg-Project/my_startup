package com.innoprog.android.feature.auth.passwordrecovery.presentation

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentPasswordRecoveryBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.passwordrecovery.di.DaggerPasswordRecoveryComponent

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<PasswordRecoveryViewModel>()
    override fun diComponent(): ScreenComponent = DaggerPasswordRecoveryComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordRecoveryBinding {
        return FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        binding.topBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        binding.btnSendCode.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Arg", binding.ivEmail.getText())
            viewModel.navigateTo(R.id.codeEntryFragment, bundle)
        }
    }
}
