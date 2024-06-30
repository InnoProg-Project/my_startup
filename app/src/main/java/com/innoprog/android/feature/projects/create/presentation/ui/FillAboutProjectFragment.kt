package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentProjectDetailsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.fillMainProjectInformation.di.DaggerFillAboutProjectComponent
import com.innoprog.android.feature.projects.create.presentation.FillAboutProjectViewModel
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent
import kotlinx.coroutines.launch

class FillAboutProjectFragment :
    BaseFragment<FragmentProjectDetailsBinding, FillAboutProjectViewModel>() {

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
    ): FragmentProjectDetailsBinding {
        return FragmentProjectDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "starting $TAG")
        binding.btLoadingLogo.setOnClickListener { imagePicker.launch(MIME_TYPE) }
        binding.topbar.setLeftIconClickListener { viewModel.navigateUp() }
        binding.deleteLogoButton.setOnClickListener {
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
                    binding.logoImageView.setImageURI(state.pinedLogoUri)
                    binding.logoIcon.isVisible = true
                    binding.tvDocDox.isVisible = false
                    binding.btLoadingLogo.isVisible = false
                }
            }
        }
    }

    companion object {
        private const val MIME_TYPE = "image/*"
        private val TAG = FillAboutProjectFragment::class.simpleName
    }
}
