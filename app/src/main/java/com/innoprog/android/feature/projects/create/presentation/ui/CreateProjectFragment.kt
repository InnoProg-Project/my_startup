package com.innoprog.android.feature.projects.create.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectCreateBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.di.DaggerCreateProjectComponent
import com.innoprog.android.feature.projects.create.presentation.CreateProjectViewModel
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent
import com.innoprog.android.feature.projects.create.presentation.ui.adapter.ItemProjectAdapter


class CreateProjectFragment :
    BaseFragment<FragmentProjectCreateBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<CreateProjectViewModel>()
    override fun diComponent(): ScreenComponent {
        return DaggerCreateProjectComponent.builder().build()
    }

    private var stepNumber = 0
    private val data = mutableListOf<String>()
    private lateinit var clickListener: (url: String) -> Unit
    private val adapter = ItemProjectAdapter(stepNumber, data) {
        clickListener
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

    private fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
        renderStep(stepNumber)
    }

    private fun renderStep(step: Int) {
        when (step) {
            STEP_ONE -> {
                binding.topbar.setTitleText(getString(R.string.step_1))
                binding.tvStepTitle.text = getString(R.string.projects_tell_about_your_project)
                // viewModel.obtainEvent(FillAboutProjectEvent.UnPinePhoto)
            }

            STEP_TWO -> {
                binding.topbar.setTitleText(getString(R.string.step_2))
                binding.tvStepTitle.text = getString(R.string.upload_photo_or_video)
            }

            STEP_THREE -> {
                binding.topbar.setTitleText(getString(R.string.step_3))
                binding.tvStepTitle.text = getString(R.string.choose_project_direction)
            }

            STEP_FOUR -> {
                binding.topbar.setTitleText(getString(R.string.step_4))
                binding.tvStepTitle.text = getString(R.string.documents)
                clickListener =  { url ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                binding.rvStepScreen.adapter = adapter
            }

            STEPS_SIZE -> {
                binding.topbar.setTitleText(getString(R.string.step_5))
                binding.tvStepTitle.text = getString(R.string.additional_information)
            }
        }
    }

    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            viewModel.obtainEvent(FillAboutProjectEvent.PickPhoto(uri))
        } else {
            Snackbar.make(
                binding.root,
                context?.getString(R.string.err_pick_photo) ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val STEPS_SIZE = 5
        const val STEP_ONE = 1
        const val STEP_TWO = 2
        const val STEP_THREE = 3
        const val STEP_FOUR = 4
    }
}
