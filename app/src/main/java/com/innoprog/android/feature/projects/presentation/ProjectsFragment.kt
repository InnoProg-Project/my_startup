package com.innoprog.android.feature.projects.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.di.DaggerProjectsComponent

class ProjectsFragment : BaseFragment<FragmentProjectsBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProjectsViewModel>()
    override fun diComponent(): ScreenComponent = DaggerProjectsComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }
}
