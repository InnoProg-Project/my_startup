package com.innoprog.android.feature.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.navOptions
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAuthorizationBinding
import com.innoprog.android.di.DaggerAppComponent
import com.innoprog.android.feature.auth.presentation.viewmodel.AuthorizationViewModel

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AuthorizationViewModel>()

    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@AuthorizationFragment)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegistration.setOnClickListener {
            viewModel.navigateTo(R.id.registrationFragment)
        }

        binding.tvPasswordRecovery.setOnClickListener {
            viewModel.navigateTo(R.id.passwordRecoveryFragment)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.navigateTo(R.id.mainFragment, bundleOf(), navOptions {
                launchSingleTop = true
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
            })
        }
    }
}
