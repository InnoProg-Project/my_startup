package com.innoprog.android.feature.profile.editingprofile.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.innoprog.android.base.BaseBottomSheetFragment
import com.innoprog.android.databinding.FragmentEditingProfileBottomSheetBinding
import com.innoprog.android.uikit.R

class EditingProfileBottomSheetFragment :
    BaseBottomSheetFragment<FragmentEditingProfileBottomSheetBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditingProfileBottomSheetBinding {
        return FragmentEditingProfileBottomSheetBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
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
}
