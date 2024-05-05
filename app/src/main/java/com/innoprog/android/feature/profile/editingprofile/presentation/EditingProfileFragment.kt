package com.innoprog.android.feature.profile.editingprofile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditingProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.editingprofile.di.DaggerEditingProfileComponent
import com.innoprog.android.feature.profile.editingprofile.domain.model.Body
import com.innoprog.android.feature.profile.editingprofile.domain.model.CompanyBody
import com.innoprog.android.feature.profile.editingprofile.presentation.state.EditProfileCompanyScreenState
import com.innoprog.android.feature.profile.editingprofile.presentation.state.EditProfileScreenState

class EditingProfileFragment : BaseFragment<FragmentEditingProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<EditingProfileViewModel>()
    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerEditingProfileComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditingProfileBinding {
        return FragmentEditingProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.editProfile()
        initButton()
        initTopBar()
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        viewModel.uiStateCompany.observe(viewLifecycleOwner) { state ->
            renderCompany(state)
        }
    }

    private fun renderCompany(screenState: EditProfileCompanyScreenState) {
        when (screenState) {
            is EditProfileCompanyScreenState.Content -> {
                fillViewsCompany(screenState.bodyInfo)
            }

            is EditProfileCompanyScreenState.Error -> {
                showError()
            }
        }
    }

    private fun render(screenState: EditProfileScreenState) {
        when (screenState) {
            is EditProfileScreenState.Content -> {
                fillViews(screenState.bodyInfo)
            }

            is EditProfileScreenState.Error -> {
                showErrorCompanyInfo()
            }
        }
    }

    private fun fillViewsCompany(companyBody: CompanyBody) {
        with(binding) {
            inputCompanyName.setText(companyBody.name)
            inputJobTitle.setText(companyBody.role)
            inputLinkToWebSite.setText(companyBody.url)
        }
    }

    private fun showErrorCompanyInfo() {
        with(binding) {
            inputCompanyName.setText("")
            inputJobTitle.setText("")
            inputLinkToWebSite.setText("")
        }
    }

    private fun fillViews(body: Body) {
        with(binding) {
            inputFIO.setText(body.name)
            inputAboutMe.setText(body.about)
        }
    }

    private fun showError() {
        with(binding) {
            inputFIO.setText("")
            inputAboutMe.setText("")
        }
    }

    private fun initButton() {

        binding.tvChangePhoto.setOnClickListener {
            viewModel.navigateTo(R.id.action_editingProfileFragment_to_editingProfileBottomSheetFragment2)
        }

        binding.buttonExit.setOnClickListener {
            viewModel.navigateTo(R.id.action_editingProfileFragment_to_dialogForExitFragment2)
        }

        binding.buttonDelete.setButtonColor(
            ContextCompat.getColor(
                requireContext(),
                com.innoprog.android.uikit.R.color.dark
            )
        )

        binding.buttonDelete.setOnClickListener {
            viewModel.navigateTo(R.id.action_editingProfileFragment_to_dialogForDeleteAccountFragment2)
        }
    }

    private fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
