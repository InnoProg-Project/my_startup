package com.innoprog.android.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow<NavEvent?>(null)
    val stateFlow: StateFlow<NavEvent?> = _stateFlow

    fun navigateTo(
        @IdRes fragmentId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null
    ) {
        _stateFlow.value = object : NavEvent {
            override fun navigate(fragment: Fragment?) {
                if (fragment != null) {
                    findNavController(fragment).navigate(fragmentId, args, navOptions)
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
                if (fragment != null) {
                    findNavController(fragment).navigateUp()
                }
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun CoroutineScope.safeLaunch(launchBody: suspend () -> Unit): Job {
        return launch(coroutineExceptionHandler) {
            launchBody.invoke()
        }
    }
}
