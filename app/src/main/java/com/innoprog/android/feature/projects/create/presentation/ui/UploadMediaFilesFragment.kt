package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentUploadImageAndVideoBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.fillMainProjectInformation.di.DaggerFillAboutProjectComponent
import com.innoprog.android.feature.projects.create.presentation.FillAboutProjectViewModel
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent

class UploadMediaFilesFragment :
    BaseFragment<FragmentUploadImageAndVideoBinding, FillAboutProjectViewModel>() {
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
    override val viewModel by injectViewModel<FillAboutProjectViewModel>()

    override fun diComponent(): ScreenComponent {
        return DaggerFillAboutProjectComponent.builder().build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUploadImageAndVideoBinding {
        return FragmentUploadImageAndVideoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
