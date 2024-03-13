package com.innoprog.android.feature.projects.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentProjectsBinding

class ProjectsFragment : BaseFragment<FragmentProjectsBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }
}
