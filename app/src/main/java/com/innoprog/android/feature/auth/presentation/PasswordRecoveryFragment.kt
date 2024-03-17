package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordRecoveryBinding {
        return FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel?.navigateUp()
        }

        binding.btnSendCode.setOnClickListener {
            viewModel?.navigateTo(R.id.codeEntryFragment)
        }
    }
}
