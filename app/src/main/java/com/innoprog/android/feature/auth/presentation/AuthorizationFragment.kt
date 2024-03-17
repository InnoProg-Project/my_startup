package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.navOptions
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
            viewModel?.navigateTo(R.id.registrationFragment)

        }

        binding.tvPasswordRecovery.setOnClickListener {
            viewModel?.navigateTo(R.id.passwordRecoveryFragment)
        }

        binding.btnLogin.setOnClickListener {
            viewModel?.navigateWithBundle(R.id.mainFragment, bundleOf(), navOptions {
                launchSingleTop = true
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
            })
        }
    }
}
