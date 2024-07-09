package com.innoprog.android.feature.auth.authorization.presentation

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
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
import com.innoprog.android.util.setOnDebouncedClickListener

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
        viewModel.verifyOnStart()

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

        binding.btnLogin.setOnDebouncedClickListener(lifecycleScope) {
            viewModel.verify(binding.ivLogin.getText(), binding.ivPassword.getText())
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
            else -> null
        }
        binding.progressBar.isVisible = state == AuthState.LOADING
        binding.blackout.isVisible = state == AuthState.LOADING
        binding.btnLogin.isVisible = state != AuthState.LOADING
        blockUserInteraction(state == AuthState.LOADING)
    }

    private fun blockUserInteraction(block: Boolean) {
        setViewAndChildrenEnabled(binding.root, !block)
    }

    private fun setViewAndChildrenEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setViewAndChildrenEnabled(child, enabled)
            }
        }
    }

    private fun navigateNext() {
        val direction =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToFeedFragment()

        viewModel.navigateTo(
            direction = direction,
            navOptions = navOptions {
                launchSingleTop = true
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
            }
        )
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
