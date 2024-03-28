package com.innoprog.android.feature.profile.editingprofile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditingProfileBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.editingprofile.di.DaggerEditingProfileComponent

class EditingProfileFragment : BaseFragment<FragmentEditingProfileBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<EditingProfileViewModel>()
    override fun diComponent(): ScreenComponent = DaggerEditingProfileComponent.builder().build()

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
    }

    private fun initButton() {

        binding.tvChangePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_editingProfileBottomSheetFragment2)
        }

        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_dialogForExitFragment2)
        }

        binding.buttonDelete.setButtonColor(
            ContextCompat.getColor(
                requireContext(),
                com.innoprog.android.uikit.R.color.dark
            )
        )

        binding.buttonDelete.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_dialogForDeleteAccountFragment2)
        }
    }

    private fun initTopBar() {
        binding.topbar.setRightIconVisibility()
        binding.topbar.setLeftIconClickListener {
            findNavController().navigateUp()
        }
    }
}
