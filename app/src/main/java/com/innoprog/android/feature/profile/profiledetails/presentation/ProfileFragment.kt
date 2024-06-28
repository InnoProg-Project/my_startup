package com.innoprog.android.feature.profile.profiledetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.profiledetails.di.DaggerProfileComponent
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.uikit.InnoProgChipGroupView

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProfileViewModel>()

    private var user: Profile? = null
    private var company: ProfileCompany? = null

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerProfileComponent
            .builder()
            .appComponent(appComponent)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        viewModel.loadProfile()

        viewModel.loadProfileCompany()

        initChips()
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            render(state)
            user = (state as? ProfileScreenState.Content)?.profileInfo
            user?.let { company?.let { it1 -> initTopBar(it, it1) } }
        }

        viewModel.uiStateCompany.observe(viewLifecycleOwner) { state ->
            renderCompany(state)
            company = (state as? ProfileCompanyScreenState.Content)?.profileCompany
            user?.let { company?.let { it1 -> initTopBar(it, it1) } }
        }
    }

    private fun initChips() {
        val chipTitles = listOf(ALL_CONTENT, PROJECT, IDEAS, LIKES, FAVORITES)
        binding.chips.setChips(chipTitles)
        binding.chips.setOnChipSelectListener(object :
            InnoProgChipGroupView.OnChipSelectListener {
            override fun onChipSelected(chipIndex: Int) {
                // Если нужно обработать чип
            }
        })
    }

    private fun initTopBar(user: Profile, company: ProfileCompany) {
        if (user != null && company != null) {
            binding.topbarProfile.setRightIconClickListener {
                val direction = ProfileFragmentDirections.actionProfileFragmentToProfileBottomSheet(
                    user,
                    company
                )
                findNavController().navigate(direction)
            }
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

    private fun renderCompany(screenStateCompany: ProfileCompanyScreenState) {
        when (screenStateCompany) {
            is ProfileCompanyScreenState.Content -> {
                fillViewsCompany(screenStateCompany.profileCompany)
            }

            is ProfileCompanyScreenState.Error -> {
                showError()
            }
        }
    }

    private fun fillViews(profile: Profile) {
        with(binding) {
            name.text = profile.name
            description.text = profile.about
        }
    }

    private fun fillViewsCompany(company: ProfileCompany) {
        with(binding) {
            position.text = company.role
            companyName.text = company.name
            profileIn.isVisible = true
        }
    }

    private fun showError() {
        with(binding) {
            name.isVisible = false
            description.isVisible = false
            position.isVisible = false
            profileIn.isVisible = false
            companyName.isVisible = false
        }
    }

    companion object {

        private const val ALL_CONTENT = "Всё"
        private const val PROJECT = "Проекты"
        private const val IDEAS = "Идеи"
        private const val LIKES = "Лайкнутые"
        private const val FAVORITES = "Избранное"
    }
}
