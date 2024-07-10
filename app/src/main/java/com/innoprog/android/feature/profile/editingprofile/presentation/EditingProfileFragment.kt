package com.innoprog.android.feature.profile.editingprofile.presentation

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
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

        initProfileInfoInput()

        initProfileCompanyInput()

        initButtonSave()
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
        val infoArgs: EditingProfileFragmentArgs by navArgs()
        val userInfo = infoArgs.user

        when (screenState) {
            is ProfileScreenState.Content -> {
                fillViews(userInfo)
            }

            is ProfileScreenState.Error -> {
                showError()
            }
        }
    }

    private fun renderCompany(screenState: ProfileCompanyScreenState) {
        val infoArgs: EditingProfileFragmentArgs by navArgs()
        val userCompany = infoArgs.userCompany

        when (screenState) {
            is ProfileCompanyScreenState.Content -> {
                fillViewsCompany(userCompany)
            }

            is ProfileCompanyScreenState.Error -> {
                showErrorCompanyInfo()
            }
        }
    }

    private fun fillViews(user: Profile) {
        with(binding) {
            inputFIO.setText(user.name)
            inputAboutMe.setText(user.about)
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
        with(binding) {

            tvChangePhoto.setOnClickListener {
                viewModel.navigateTo(R.id.action_editingProfileFragment_to_editingProfileBottomSheetFragment2)
            }

            buttonExit.setOnClickListener {
                viewModel.navigateTo(R.id.action_editingProfileFragment_to_dialogForExitFragment2)
            }

            buttonDelete.setButtonColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.innoprog.android.uikit.R.color.dark
                )
            )

            buttonDelete.setOnClickListener {
                viewModel.navigateTo(R.id.action_editingProfileFragment_to_dialogForDeleteAccountFragment2)
            }
        }
    }

    private fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }

    private fun initProfileInfoInput() {
        with(binding) {
            inputFIO.setInputType(InputType.TYPE_CLASS_TEXT)
            inputAboutMe.setInputType(InputType.TYPE_CLASS_TEXT)
            inputFIO.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
            })
            inputAboutMe.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

    private fun initProfileCompanyInput() {
        with(binding) {
            inputCompanyName.setInputType(InputType.TYPE_CLASS_TEXT)
            inputLinkToWebSite.setInputType(InputType.TYPE_CLASS_TEXT)
            inputJobTitle.setInputType(InputType.TYPE_CLASS_TEXT)
            inputCompanyName.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
            })
            inputLinkToWebSite.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            inputJobTitle.addTextChangedListener(object : TextChangedListener {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        }
    }

    private fun initButtonSave() {
        with(binding) {
            val name = inputFIO.toString()
            val about = inputAboutMe.toString()
            val companyTitle = inputCompanyName.toString()
            val url = inputLinkToWebSite.toString()
            val role = inputJobTitle.toString()

            buttonSave.setOnClickListener {
                viewModel.editProfile(
                    name = name,
                    about = about
                )
                viewModel.editProfileCompany(
                    name = companyTitle,
                    url = url,
                    role = role
                )
            }
        }
    }
}


