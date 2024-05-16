package com.innoprog.android.feature.edit.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCreateEditContentBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.edit.di.DaggerCreateEditContentComponent

class CreateEditContentFragment : BaseFragment<FragmentCreateEditContentBinding, BaseViewModel>() {

    private val args by navArgs<CreateEditContentFragmentArgs>()
    override val viewModel by injectViewModel<CreateEditContentViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerCreateEditContentComponent
            .builder()
            .appComponent(appComponent)
            .build()
    }

    private val pickMediaLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.path?.let {
                viewModel.addMediaToLoadList(it)
                Log.e("qwe", it)
            }

        }

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

        viewModel.setEditorType(args.typeContent)

        binding.topBar.setLeftIconClickListener {
            viewModel.navigateUp()
        }

        binding.saveBV.setOnClickListener {
            // clickButtonSave()
        }

        binding.loadBV.setOnClickListener { loadMedia() }

    }

    private fun render(state: CreateEditContentState) {
        when (state) {
            is CreateEditContentState.CreateIdea -> {
                binding.topBar.setTitleText(getText(R.string.create_idea))
                binding.saveBV.setText(getString(R.string.publish))
                binding.inputTitle.setHintText(getString(R.string.title_of_idea))
                binding.inputText.setHintText(getString(R.string.text_idea))
                binding.groupProject.visibility = View.GONE

            }

            is CreateEditContentState.CreatePublication -> {
                binding.topBar.setTitleText(getText(R.string.create_publish))
                binding.saveBV.setText(getString(R.string.publish))
                binding.inputTitle.setHintText(getString(R.string.title_of_news))
                binding.inputText.setHintText(getString(R.string.text_publish))
                binding.groupProject.visibility = View.VISIBLE
            }

            is CreateEditContentState.EditPublication -> {
                binding.topBar.setTitleText(getText(R.string.edit_publish))
                binding.saveBV.setText(getString(R.string.save))
                binding.groupProject.visibility = View.VISIBLE
            }
        }
    }

    private fun loadMedia() {
        pickMediaLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageAndVideo
            )
        )
    }
}
