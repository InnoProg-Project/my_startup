package com.innoprog.android.feature.auth.authorization.presentation

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.navOptions
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAuthorizationBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.authorization.di.DaggerAuthorizationComponent

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AuthorizationViewModel>()
    private var isVisiblePassword = false

    override fun diComponent(): ScreenComponent = DaggerAuthorizationComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        binding.ivPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        renderIVPassword()
        binding.btRegistration.setOnClickListener {
            viewModel.navigateTo(R.id.registrationFragment)
        }

        binding.tvPasswordRecovery.setOnClickListener {
            viewModel.navigateTo(R.id.passwordRecoveryFragment)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.verify(binding.ivLogin.getText(), binding.ivPassword.getText())
            viewModel.navigateTo(R.id.mainFragment, bundleOf(), navOptions {
                launchSingleTop = true
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
            })
        }

        binding.ivPassword.setRightIconClickListener {
            isVisiblePassword = !isVisiblePassword
            renderIVPassword()
        }
    }

    private fun renderIVPassword() {
        if (isVisiblePassword) {
            binding.ivPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            binding.ivPassword.setRightIcon(R.drawable.eye_off)
        } else {
            binding.ivPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
            binding.ivPassword.setRightIcon(R.drawable.eye)
        }
    }
}
