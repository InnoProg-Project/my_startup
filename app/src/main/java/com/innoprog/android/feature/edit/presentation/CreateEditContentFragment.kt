package com.innoprog.android.feature.edit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCreateEditContentBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.edit.di.DaggerCreateEditContentComponent

class CreateEditContentFragment : BaseFragment<FragmentCreateEditContentBinding, BaseViewModel>() {

    private val args: TypeContentArgs by navArgs()

    override val viewModel by injectViewModel<CreateEditContentViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCreateEditContentComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateEditContentBinding {
        return FragmentCreateEditContentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        args.let { viewModel.setEditorType(it) }

        binding.topBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }

    }

    private fun render(state: CreateEditContentState) {
        when (state) {
            is CreateEditContentState.CreateIdea -> {
                binding.topBar.setTitleText(getText(R.string.create_idea))
                binding.saveBV.setText(getString(R.string.publish))
                binding.groupProject.visibility = View.GONE

            }

            is CreateEditContentState.CreatePublication -> {
                binding.topBar.setTitleText(getText(R.string.create_publish))
                binding.saveBV.setText(getString(R.string.publish))
                binding.groupProject.visibility = View.VISIBLE

            }

            is CreateEditContentState.EditPublication -> {
                binding.topBar.setTitleText(getText(R.string.edit_publish))
                binding.saveBV.setText(getString(R.string.save))
                binding.groupProject.visibility = View.VISIBLE
            }
        }
    }
}
