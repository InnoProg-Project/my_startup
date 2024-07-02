package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.innoprog.android.R
import com.innoprog.android.databinding.FragmentProjectDetailsBinding
import com.innoprog.android.feature.projects.create.presentation.model.FillAboutProjectEvent
import kotlinx.coroutines.launch

class FillAboutProject(val binding: FragmentProjectDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(deleteClickListener: () -> Unit) {

    }

    val imagePicker = registerForActivityResult(
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

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v(TAG, "starting $TAG")
        binding.btLoadingLogo.setOnClickListener { imagePicker.launch(MIME_TYPE) }
        binding.btDeleteLogo.setOnClickListener {

        }
        lifecycleScope.launch {
            viewModel.state.collect { state ->
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
    }

    companion object {
        private const val MIME_TYPE = "image/*"
    }
}
