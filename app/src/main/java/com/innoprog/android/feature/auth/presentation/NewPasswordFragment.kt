package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.passwordRecoveryFragment)
        }
    }
}
