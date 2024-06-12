package com.innoprog.android.feature.auth.authorization.presentation

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.navOptions
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAuthorizationBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.authorization.di.DaggerAuthorizationComponent
import com.innoprog.android.feature.auth.authorization.domain.model.AuthState
import com.innoprog.android.uikit.InnoProgInputViewState

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AuthorizationViewModel>()
    private var isVisiblePassword = false

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerAuthorizationComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeIV()
        renderIVPassword()
        viewModel.observeState().observe(viewLifecycleOwner) {
            renderResult(it)
        }

        binding.btRegistration.setOnClickListener {
            viewModel.navigateTo(R.id.registrationFragment)
        }

        binding.tvPasswordRecovery.setOnClickListener {
            viewModel.navigateTo(R.id.passwordRecoveryFragment)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.verify(binding.ivLogin.getText(), binding.ivPassword.getText())
        }

        binding.topBar.setRightIconClickListener {
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

    private fun renderResult(state: AuthState) {
        when (state) {
            AuthState.SUCCESS -> navigateNext()
            AuthState.CONNECTION_ERROR -> renderError(getString(R.string.authorization_no_internet))
            AuthState.VERIFICATION_ERROR -> renderError(getString(R.string.authorization_bad_data))
            AuthState.INPUT_ERROR -> renderError(getString(R.string.authorization_bad_data))
        }
    }

    private fun navigateNext() {
        viewModel.navigateTo(R.id.mainFragment, bundleOf(), navOptions {
            launchSingleTop = true
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
        })
    }

    private fun renderError(message: String) {
        binding.ivLogin.renderState(InnoProgInputViewState.ERROR)
        binding.ivPassword.renderState(InnoProgInputViewState.ERROR)
        binding.ivPassword.setCaptionText(message)
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

    private fun customizeIV() {
        binding.ivLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        binding.ivLogin.setSingleLine(true)
        binding.ivPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        binding.ivPassword.setSingleLine(true)
    }
}
