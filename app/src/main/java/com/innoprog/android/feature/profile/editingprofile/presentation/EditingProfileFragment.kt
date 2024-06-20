package com.innoprog.android.feature.profile.editingprofile.presentation

import android.os.Bundle
import android.text.InputType
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
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileCompanyScreenState
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileScreenState
import com.innoprog.android.feature.projects.projectdetails.presentation.TextChangedListener

class EditingProfileFragment : BaseFragment<FragmentEditingProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<EditingProfileViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerEditingProfileComponent
            .builder()
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

        initButton()

        initTopBar()

        observeData()
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        viewModel.uiStateCompany.observe(viewLifecycleOwner) { state ->
            renderCompany(state)
        }
    }

    private fun render(screenState: ProfileScreenState) {
        when (screenState) {
            is ProfileScreenState.Content -> {
                fillViews(screenState.profileInfo)
            }

            is ProfileScreenState.Error -> {
                showError()
            }
        }
    }

    private fun renderCompany(screenState: ProfileCompanyScreenState) {
        when (screenState) {
            is ProfileCompanyScreenState.Content -> {
                fillViewsCompany(screenState.profileCompany)
            }

            is ProfileCompanyScreenState.Error -> {
                showErrorCompanyInfo()
            }
        }
    }

    private fun fillViews(profile: Profile) {
        with(binding) {
            inputFIO.setText(profile.name)
            inputAboutMe.setText(profile.about)
            viewModel.editProfile(profile.name, profile.about)
        }
    }

    private fun showError() {
        with(binding) {
            inputFIO.setText("")
            inputAboutMe.setText("")
        }
    }

    private fun fillViewsCompany(company: ProfileCompany) {
        with(binding) {
            inputCompanyName.setText(company.name)
            inputJobTitle.setText(company.role)
            inputLinkToWebSite.setText(company.url)
            viewModel.editProfileCompany(company.name, company.url, company.role)
        }
    }

    private fun showErrorCompanyInfo() {
        with(binding) {
            inputCompanyName.setText("")
            inputJobTitle.setText("")
            inputLinkToWebSite.setText("")
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

    private fun initProfileInfoInput(name: String, about: String) {
        with(binding) {
            inputFIO.setInputType(InputType.TYPE_CLASS_TEXT)
            inputAboutMe.setInputType(InputType.TYPE_CLASS_TEXT)
            inputFIO.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.editProfile(s.toString(name))
                }
            })
            inputAboutMe.addTextChangedListener((object : TextChangedListener {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.editProfile(s.toString(about))
                }
            }))
        }
    }

}
