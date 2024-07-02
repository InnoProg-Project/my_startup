package com.innoprog.android.feature.projects.create.presentation.ui

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentProjectUploadImageAndVideoBinding
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent

class UploadMediaFilesFragment :
    BaseFragment<FragmentProjectUploadImageAndVideoBinding, FillAboutProjectViewModel>() {
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
}
