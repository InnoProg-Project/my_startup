package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectAdditionalInformationBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.presentation.CreateProjectViewModel
import com.innoprog.android.feature.projects.create.project.presentation.AdditionalInformationViewModel

class AdditionalInformationFragment :
    BaseFragment<FragmentProjectAdditionalInformationBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<CreateProjectViewModel>()
    override fun diComponent(): ScreenComponent {
        TODO("Not yet implemented")
    }


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectAdditionalInformationBinding {
        return FragmentProjectAdditionalInformationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopBar()
        binding.buttonSave.setOnClickListener {
            viewModel.setProject()
        }
    }

    private fun initTopBar() {
        binding.topbarAdditionalInformation.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
