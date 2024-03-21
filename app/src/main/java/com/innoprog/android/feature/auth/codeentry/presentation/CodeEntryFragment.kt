package com.innoprog.android.feature.auth.codeentry.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCodeEntryBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.codeentry.di.DaggerCodeEntryComponent

class CodeEntryFragment : BaseFragment<FragmentCodeEntryBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CodeEntryViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCodeEntryComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeEntryBinding {
        return FragmentCodeEntryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateUp()
        }
    }
}
