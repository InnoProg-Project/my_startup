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
import com.innoprog.android.feature.projects.create.presentation.ui.adapter.DocumentsAdapter

class CreateFragment :
    BaseFragment<FragmentProjectCreateBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CreateProjectViewModel>()
    override fun diComponent(): ScreenComponent {
        return DaggerCreateProjectComponent.builder().build()
    }

    private var stepNumber = 0
    private var adapter: DocumentsAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectCreateBinding {
        return FragmentProjectCreateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopBar()
        renderStep(stepNumber)
        binding.bvResume.setOnClickListener {
            if (stepNumber < STEPS_SIZE) {
                stepNumber++
                renderStep(stepNumber)
            } else {
                viewModel.createProject()
            }
        }
    }

    private fun renderStep(step: Int){
        when(step){
            1 -> {
                binding.topbar.setTitleText(getString(R.string.step_1))
                binding.tvStepTitle.text = getString(R.string.projects_tell_about_your_project)
            }
            2 -> {
                binding.topbar.setTitleText(getString(R.string.step_2))
                binding.tvStepTitle.text = getString(R.string.upload_photo_or_video)
            }
            3 ->{
                binding.topbar.setTitleText(getString(R.string.step_3))
                binding.tvStepTitle.text = getString(R.string.choose_project_direction)
            }
            4 ->{
                binding.topbar.setTitleText(getString(R.string.step_4))
                binding.tvStepTitle.text = getString(R.string.documents)
            }
            5 ->{
                binding.topbar.setTitleText(getString(R.string.step_5))
                binding.tvStepTitle.text = getString(R.string.additional_information)
            }
        }
    }
    fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
            adapter
        }
    }

    companion object {
        const val STEPS_SIZE = 5
    }
}
