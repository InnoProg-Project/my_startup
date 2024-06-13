package com.innoprog.android.feature.projects.projectdetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectDetailsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.projectdetails.di.DaggerProjectDetailsComponent

class ProjectDetailsFragment : BaseFragment<FragmentProjectDetailsBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProjectDetailsViewModel>()
    private var selectedImageUri: Uri? = null
    override fun diComponent(): ScreenComponent = DaggerProjectDetailsComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectDetailsBinding {
        Log.v(TAG, "starting $TAG")
        return FragmentProjectDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        val projectNameTextWatcher = object : TextChangedListener {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkConditionsAndSetSubmitButton()
            }
        }
        binding.projectName.addTextChangedListener(projectNameTextWatcher)
        val shortDescriptionTextWatcher = object : TextChangedListener {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkConditionsAndSetSubmitButton()
            }
        }
        binding.ShortDescription.addTextChangedListener(shortDescriptionTextWatcher)
    }

    private fun initButton() {
        binding.topbar.setLeftIconClickListener { viewModel.navigateUp() }
        binding.btLoadingLogo.setOnClickListener {
            openImagePicker()
        }

        binding.deleteLogoButton.setOnClickListener {
            binding.logoImageView.setImageDrawable(null)
            showButtonAddIcon()
            selectedImageUri = null
            checkConditionsAndSetSubmitButton()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.logoImageView.setImageURI(uri)
                showLogoIcon()
                checkConditionsAndSetSubmitButton()
            }
        }
    }

    private fun showLogoIcon() {
        binding.logoImageView.visibility = View.VISIBLE
        binding.deleteLogoButton.visibility = View.VISIBLE
        binding.tvDocDox.visibility = View.GONE
        binding.btLoadingLogo.visibility = View.GONE
    }

    private fun showButtonAddIcon() {
        binding.logoImageView.visibility = View.GONE
        binding.deleteLogoButton.visibility = View.GONE
        binding.tvDocDox.visibility = View.VISIBLE
        binding.btLoadingLogo.visibility = View.VISIBLE
    }

    private fun checkConditionsAndSetSubmitButton() {
        val projectName = binding.projectName.getText()
        val shortDescription = binding.ShortDescription.getText()

        val isIconAdded = selectedImageUri != null

        if (projectName.isNotEmpty() && shortDescription.isNotEmpty() && isIconAdded) {
            binding.btOntinue.stateIsEnabled(true)
        } else {
            binding.btOntinue.stateIsEnabled(false)
        }
    }

    companion object {
        private const val IMAGE_PICKER_REQUEST_CODE = 100
        private val TAG = ProjectDetailsFragment::class.simpleName
    }
}
