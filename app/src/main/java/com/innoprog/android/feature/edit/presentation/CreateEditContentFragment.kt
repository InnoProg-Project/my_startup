package com.innoprog.android.feature.edit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentCreateEditContentBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.edit.di.DaggerCreateEditContentComponent

class CreateEditContentFragment : BaseFragment<FragmentCreateEditContentBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<CreateEditContentViewModel>()
    override fun diComponent(): ScreenComponent = DaggerCreateEditContentComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateEditContentBinding {
        return FragmentCreateEditContentBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_edit_content, container, false)
    }
}
