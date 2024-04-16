package com.innoprog.android.feature.projects.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAdditionalInformationBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.di.DaggerAdditionalInformationComponent

class AdditionalInformationFragment : BaseFragment<FragmentAdditionalInformationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AdditionalInformationViewModel>()
    override fun diComponent(): ScreenComponent = DaggerAdditionalInformationComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdditionalInformationBinding {
        return FragmentAdditionalInformationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopBar()
    }

    private fun initTopBar() {
        binding.topbarAdditionalInformation.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
