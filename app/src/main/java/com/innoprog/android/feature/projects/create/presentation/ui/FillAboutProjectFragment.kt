package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.databinding.FragmentProjectDetailsBinding
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent
import kotlinx.coroutines.launch

class FillAboutProjectFragment : Fragment() {

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

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectDetailsBinding {
        return FragmentProjectDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "starting $TAG")
        binding.btLoadingLogo.setOnClickListener { imagePicker.launch(MIME_TYPE) }
        binding.topbar.setLeftIconClickListener { viewModel.navigateUp() }
        binding.btDeleteLogo.setOnClickListener {
            viewModel.obtainEvent(FillAboutProjectEvent.UnPinePhoto)
        }
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                Log.v(TAG, "state = $state")
                if (state.pinedLogoUri == null) {
                    binding.logoIcon.isVisible = false
                    binding.btLoadingLogo.isVisible = true
                    binding.tvDocDox.isVisible = true
                } else {
                    binding.ivLogo.setImageURI(state.pinedLogoUri)
                    binding.logoIcon.isVisible = true
                    binding.tvDocDox.isVisible = false
                    binding.btLoadingLogo.isVisible = false
                }
            }
        }
        binding.btResume.setOnClickListener {
            viewModel.navigateTo(R.id.action_fillAboutProjectFragment_to_uploadMediaFilesFragment)
        }
    }

    companion object {
        private const val MIME_TYPE = "image/*"
        private val TAG = FillAboutProjectFragment::class.simpleName
    }
}
