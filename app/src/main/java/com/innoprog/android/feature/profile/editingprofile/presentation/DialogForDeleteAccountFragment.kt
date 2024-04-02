package com.innoprog.android.feature.profile.editingprofile.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.innoprog.android.base.BaseDialogFragment
import com.innoprog.android.databinding.FragmentDialogForDeleteAccountBinding

class DialogForDeleteAccountFragment : BaseDialogFragment<FragmentDialogForDeleteAccountBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDialogForDeleteAccountBinding {
        return FragmentDialogForDeleteAccountBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }

    private fun initButton() {
        binding.buttonDelete.setOnClickListener {
            Toast.makeText(requireContext(), "нажата кнопка удалить", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        return dialog
    }
}
