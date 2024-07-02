package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectCreateBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.di.DaggerCreateProjectComponent
import com.innoprog.android.feature.projects.create.presentation.CreateProjectViewModel

class CreateFragment :
    BaseFragment<FragmentProjectCreateBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CreateProjectViewModel>()
    override fun diComponent(): ScreenComponent {
        return DaggerCreateProjectComponent.builder().build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectCreateBinding {
        return FragmentProjectCreateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopBar()
        binding.bvResume.setOnClickListener {
            viewModel.navigateTo(R.id.action_editingDocksFragment_to_additionalInformationFragment)
        }
    }

    fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
