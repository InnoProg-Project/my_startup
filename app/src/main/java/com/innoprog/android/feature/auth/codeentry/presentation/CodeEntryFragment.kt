package com.innoprog.android.feature.auth.codeentry.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCodeEntryBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.codeentry.di.DaggerCodeEntryComponent
import com.innoprog.android.uikit.InnoProgInputViewState
import okhttp3.internal.format

class CodeEntryFragment : BaseFragment<FragmentCodeEntryBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CodeEntryViewModel>()
    private var email: String? = null
    override fun diComponent(): ScreenComponent = DaggerCodeEntryComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeEntryBinding {
        return FragmentCodeEntryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = arguments?.getString("Arg")
        binding.tvSetCodeText.text =
            format(getString(R.string.verification_email_information), email ?: "")
        binding.cvEmailInput.setState(InnoProgInputViewState.FOCUSED)
        binding.topBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
            renderButton(it.first, it.second)
        }
        viewModel.startTimer()
        binding.btnSendCode.setOnClickListener {
            viewModel.repeatRequest()
        }
    }

    private fun renderButton(state: Boolean, message: String) {
        binding.btnSendCode.stateIsEnabled(state)
        binding.btnSendCode.setText(message)
    }
}
