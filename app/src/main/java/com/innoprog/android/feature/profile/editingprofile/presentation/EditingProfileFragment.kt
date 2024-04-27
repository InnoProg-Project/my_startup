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
        initButton()
        initTopBar()
    }

    private fun observeData() {
        viewModel.saveProfile()
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
