package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.base.ViewModelSample
import com.innoprog.android.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    override val viewModel: BaseViewModel by viewModels<ViewModelSample>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
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
