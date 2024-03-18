package com.innoprog.android.feature.projects.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectsBinding
import com.innoprog.android.base.ViewModelSample

class ProjectsFragment : BaseFragment<FragmentProjectsBinding>() {
    override val viewModel: BaseViewModel by viewModels<ViewModelSample>()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }
}
