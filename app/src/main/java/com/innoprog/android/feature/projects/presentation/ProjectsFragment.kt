package com.innoprog.android.feature.projects.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectsBinding
import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.DaggerAppComponent

class ProjectsFragment : BaseFragment<FragmentProjectsBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProjectsViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@ProjectsFragment)
        super.onCreate(savedInstanceState)
    }
}
