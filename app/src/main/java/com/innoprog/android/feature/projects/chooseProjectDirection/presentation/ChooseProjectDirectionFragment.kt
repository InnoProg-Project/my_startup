package com.innoprog.android.feature.projects.chooseProjectDirection.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditProjectDirectionBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.chooseProjectDirection.di.DaggerChooseProjectDirectionComponent

class ChooseProjectDirectionFragment : BaseFragment<FragmentEditProjectDirectionBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ChooseProjectDirectionViewModel>()
    override fun diComponent(): ScreenComponent = DaggerChooseProjectDirectionComponent.builder().build()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentEditProjectDirectionBinding {
        return FragmentEditProjectDirectionBinding.inflate(inflater, container, false)
    }
}