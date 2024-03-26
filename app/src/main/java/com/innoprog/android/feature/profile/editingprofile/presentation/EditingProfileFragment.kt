package com.innoprog.android.feature.profile.editingprofile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun initButton() {

        binding.tvChangePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_editingProfileBottomSheetFragment)
        }

        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_dialogForExitFragment)
        }

        binding.buttonDelete.setOnClickListener {
            findNavController().navigate(R.id.action_editingProfileFragment_to_dialogForDeleteAccountFragment)
        }
    }
}
