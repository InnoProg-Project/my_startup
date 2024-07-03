package com.innoprog.android.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.innoprog.android.R
import com.innoprog.android.util.debounceUnitFun
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    private val debounceNavigateTo =
        debounceUnitFun<Fragment?>(CLICK_DELAY, viewModelScope, false)

    private val _stateFlow = MutableStateFlow<NavEvent?>(null)
    val stateFlow: StateFlow<NavEvent?> = _stateFlow

    fun navigateTo(
        @IdRes fragmentId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null
    ) {
        _stateFlow.value = object : NavEvent {
            override fun navigate(fragment: Fragment?) {
                debounceNavigateTo(fragment) { fragment ->
                    if (fragment != null) {
                        findNavController(fragment).navigate(fragmentId, args, navOptions)
                    }
                }
            }
        }
    }

    fun navigateTo(
        direction: NavDirections,
        navOptions: NavOptions? = null
    ) {
        _stateFlow.value = object : NavEvent {
            override fun navigate(fragment: Fragment?) {
                debounceNavigateTo(fragment) { fragment ->
                    if (fragment != null) {
                        findNavController(fragment).navigate(direction, navOptions)
                    }
                }
            }
        }
    }

    fun navigateToStart() {
        _stateFlow.value = object : NavEvent {
            override fun navigate(fragment: Fragment?) {
                debounceNavigateTo(fragment) { fragment ->
                    if (fragment != null) {
                        val navFragmentId = R.id.authorizationFragment
                        val navOptions =
                            NavOptions.Builder().setPopUpTo(navFragmentId, true).build()
                        findNavController(fragment).navigate(navFragmentId, null, navOptions)
                    }
                }
            }
        }
    }

    fun setState(state: NavEvent?) {
        _stateFlow.value = state
    }

    fun navigateUp() {
        _stateFlow.value = object : NavEvent {
            override fun navigate(fragment: Fragment?) {
                debounceNavigateTo(fragment) { fragment ->
                    if (fragment != null) {
                        findNavController(fragment).navigateUp()
                    }
                }
            }
        }
    }

    companion object {
        const val CLICK_DELAY = 300L
    }
}
