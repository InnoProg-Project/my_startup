package com.innoprog.android.feature.editingprofile.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditingProfileBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.editingprofile.di.DaggerEditingProfileComponent
import com.innoprog.android.uikit.InnoProgButtonView

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
            findNavController().navigate(R.id.action_editingProfileFragment_to_bottomSheetFragment)
        }

        binding.buttonExit.setOnClickListener {
            showDialogForExit()
        }

        binding.buttonDelete.setOnClickListener {
            showDialogForDeleteAccount()
        }
    }

    private fun showDialogForExit() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_for_exit)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val exitButtom: InnoProgButtonView = dialog.findViewById(R.id.buttonExit)
        val canncelButton: InnoProgButtonView = dialog.findViewById(R.id.buttonCancel)

        exitButtom.setOnClickListener {
            Toast.makeText(requireContext(), "нажата кнопка выход", Toast.LENGTH_SHORT).show()
        }

        canncelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDialogForDeleteAccount() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_for_delete_account)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val deleteButtom: InnoProgButtonView = dialog.findViewById(R.id.buttonDelete)
        val cancelButton: InnoProgButtonView = dialog.findViewById(R.id.buttonCancel)

        deleteButtom.setOnClickListener {
            Toast.makeText(requireContext(), "нажата кнопка удалить", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
