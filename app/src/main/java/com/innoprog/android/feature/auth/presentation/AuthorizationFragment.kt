package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegistration.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

        binding.tvPasswordRecovery.setOnClickListener {
            findNavController().navigate(R.id.passwordRecoveryFragment)
        }
    }
}
