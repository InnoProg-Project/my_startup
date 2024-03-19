package com.innoprog.android.feature.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCodeEntryBinding
import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.DaggerAppComponent
import com.innoprog.android.feature.auth.presentation.viewmodel.CodeEntryViewModel

class CodeEntryFragment : BaseFragment<FragmentCodeEntryBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CodeEntryViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeEntryBinding {
        return FragmentCodeEntryBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@CodeEntryFragment)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateUp()
        }
    }
}
