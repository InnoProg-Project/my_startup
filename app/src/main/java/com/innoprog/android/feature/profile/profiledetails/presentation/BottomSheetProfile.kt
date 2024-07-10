package com.innoprog.android.feature.profile.profiledetails.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.innoprog.android.base.BaseBottomSheetFragment
import com.innoprog.android.databinding.BottomSheetProfileBinding
import com.innoprog.android.uikit.R
import com.innoprog.android.util.debounceUnitFun

class BottomSheetProfile : BaseBottomSheetFragment<BottomSheetProfileBinding>() {

    private val debounceNavigateTo = debounceUnitFun<Fragment?>(lifecycleScope)

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BottomSheetProfileBinding {
        return BottomSheetProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()

        navigateTo()
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

    private fun navigateTo() = with(binding) {
        editText.setOnClickListener {
            debounceNavigateTo(this@BottomSheetProfile) { fragment ->
                findNavController().navigate(
                    com.innoprog.android.R.id.action_profile_bottom_sheet_to_editingProfileFragment
                )
            }
        }

        infoText.setOnClickListener {
            debounceNavigateTo(this@BottomSheetProfile) { fragment ->
                findNavController().navigate(
                    com.innoprog.android.R.id.action_profile_bottom_sheet_to_aboutAppFragment
                )
            }
        }

        docksText.setOnClickListener {
            debounceNavigateTo(this@BottomSheetProfile) { fragment ->
                findNavController().navigate(
                    com.innoprog.android.R.id.action_profile_bottom_sheet_to_legalDocumentsFragment
                )
            }
        }
    }

    companion object {
        const val CLICK_DELAY = 300L
    }
}
