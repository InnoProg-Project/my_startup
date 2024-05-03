package com.innoprog.android.feature.projects.projectsScreen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel

class ProjectsScreenFragment : BaseFragment<FragmentProjectsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProjectsScreenViewModel>()
    override fun diComponent(): ScreenComponent = DaggerProjectsComponent.builder().build()

    private val adapter by lazy {
        ProjectsScreenAdapter(requireContext()) {
            // TODO переход на экран проекта
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        binding.projectsRV.adapter = adapter
        binding.createNewProjectButton.setOnClickListener {
            // TODO переход на экран создания проекта
        }
        binding.createFirstProjectButton.setOnClickListener {
            // TODO переход на экран создания проекта
        }
    }

    private fun render(state: ProjectsScreenState) {
        when (state) {
            is ProjectsScreenState.Content -> showContent(state.projects)
            is ProjectsScreenState.Empty -> showEmpty()
        }
    }

    private fun showContent(items: List<ProjectScreenModel>) {
        binding.projectPlaceholderIV.visibility = View.GONE
        binding.projectPlaceholderTV.visibility = View.GONE
        binding.createFirstProjectButton.visibility = View.GONE
        binding.createNewProjectButton.visibility = View.VISIBLE
        binding.projectsRV.visibility = View.VISIBLE

        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.projectPlaceholderIV.visibility = View.VISIBLE
        binding.projectPlaceholderTV.visibility = View.VISIBLE
        binding.createFirstProjectButton.visibility = View.VISIBLE
        binding.createNewProjectButton.visibility = View.GONE
        binding.projectsRV.visibility = View.GONE
    }
}
