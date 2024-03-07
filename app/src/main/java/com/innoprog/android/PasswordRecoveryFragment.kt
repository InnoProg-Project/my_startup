package com.innoprog.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.innoprog.android.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : BindingFragment<FragmentPasswordRecoveryBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordRecoveryBinding {
        return FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSendCode.setOnClickListener {
            findNavController().navigate(R.id.codeEntryFragment)
        }

    }
}
