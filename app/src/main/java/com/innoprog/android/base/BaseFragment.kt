package com.innoprog.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.innoprog.android.di.ScreenComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    private var _binding: T? = null
    protected val binding get() = _binding!!

    open val viewModelFactory: ViewModelProvider.Factory by lazy {
        val component = diComponent()
        component.viewModelFactory
    }

    protected abstract fun diComponent(): ScreenComponent

    inline fun <reified VM : BaseViewModel> injectViewModel() = viewModels<VM>(
        factoryProducer = { viewModelFactory }
    )

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateFlow.collectLatest { navEvent ->
                navEvent?.navigate(this@BaseFragment)
            }
        }
        initViews()
        subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.setState(null)
    }

    open protected fun initViews() = Unit
    open protected fun subscribe() = Unit
}
