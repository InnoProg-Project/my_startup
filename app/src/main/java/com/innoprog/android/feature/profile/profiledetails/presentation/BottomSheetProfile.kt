package com.innoprog.android.feature.profile.profiledetails.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.innoprog.android.base.BaseBottomSheetFragment
import com.innoprog.android.databinding.BottomSheetProfileBinding
import com.innoprog.android.feature.profile.editingprofile.presentation.EditingProfileFragmentArgs
import com.innoprog.android.feature.profile.profiledetails.domain.models.Profile
import com.innoprog.android.feature.profile.profiledetails.domain.models.ProfileCompany
import com.innoprog.android.uikit.R

class BottomSheetProfile : BaseBottomSheetFragment<BottomSheetProfileBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetProfileBinding {
        return BottomSheetProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoArgs: EditingProfileFragmentArgs by navArgs()
        val userInfo = infoArgs.user
        val company = infoArgs.userCompany

        initButton()

        navigateTo(userInfo, company)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(
            requireContext(),
            R.style.TransparentDialog
        )
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun initButton() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun navigateTo(user: Profile, userCompany: ProfileCompany) {
        with(binding) {
            editText.setOnClickListener {
                val direction = BottomSheetProfileDirections.actionProfileBottomSheetToEditingProfileFragment(user, userCompany)
                findNavController().navigate(direction)
            }

            infoText.setOnClickListener {
                val direction = BottomSheetProfileDirections.actionProfileBottomSheetToAboutAppFragment()
                findNavController().navigate(direction)

            }

            docksText.setOnClickListener {
                val direction = BottomSheetProfileDirections.actionProfileBottomSheetToLegalDocumentsFragment()
                findNavController().navigate(direction)
            }
        }
    }
}
