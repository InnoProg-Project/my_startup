package com.innoprog.android.feature.projects.projectsScreen.presentation

import DaggerProjectsFragmentComponent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectsBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.util.ErrorScreenState
import kotlinx.coroutines.launch

class ProjectsScreenFragment : BaseFragment<FragmentProjectsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<ProjectsScreenViewModel>()
    override fun diComponent(): ScreenComponent =
        DaggerProjectsFragmentComponent.builder()
            .appComponent(AppComponentHolder.getComponent())
            .build()

    private val adapter = ProjectsScreenAdapter {} // Добавить переход на экран проекта

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectsBinding {
        return FragmentProjectsBinding.inflate(inflater, container, false)
    }

    override fun subscribe() {
        super.subscribe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                render(state)
            }
        }
        with(binding) {
            ipbtenCreateNewProject.setOnClickListener {
                val direction = ProjectsScreenFragmentDirections
                    .actionProjectsFragmentToFillAboutProjectFragment()
                findNavController().navigate(direction)
            }

            ipbtnCreateFisrtProject.setOnClickListener {
                viewModel.navigateTo(R.id.fillAboutProjectFragment)
            }

            layoutErrorScreen.findViewById<com.innoprog.android.uikit.InnoProgButtonView>(
                com.innoprog.android.uikit.R.id.ipbtn_repeat_request
            ).setOnClickListener {
                viewModel.getProjectList()
            }
        }
    }

    override fun initViews() {
        super.initViews()
        prepareAdapter()
    }

    private fun prepareAdapter() {
        binding.tvProjectList.adapter = adapter
    }

    private fun render(state: ProjectsScreenState) {
        when (state) {
            is ProjectsScreenState.Content -> showContent(state.projects)
            is ProjectsScreenState.Empty -> showEmpty()
            is ProjectsScreenState.Loading -> showEmpty() // заменить на экран загрузки
            is ProjectsScreenState.Error -> {
                renderError()
                fetchErrorScreen(state.errorType)
            }
        }
    }

    private fun showContent(items: List<Project>) = with(binding) {
        adapter.setListItems(items)

        listOf(
            ivEmptyListPlaceholder,
            tvEmptyListPlaceholder,
            ipbtnCreateFisrtProject,
            layoutErrorScreen
        ).forEach {
            it.isVisible = false
        }
        listOf(ipbtenCreateNewProject, tvProjectList).forEach {
            it.isVisible = true
        }
    }

    private fun showEmpty() = with(binding) {
        listOf(ivEmptyListPlaceholder, tvEmptyListPlaceholder, ipbtnCreateFisrtProject).forEach {
            it.isVisible = true
        }
        listOf(ipbtenCreateNewProject, tvProjectList, layoutErrorScreen).forEach {
            it.isVisible = false
        }
    }

    private fun renderError() = with(binding) {
        listOf(
            ivEmptyListPlaceholder, tvEmptyListPlaceholder, ipbtnCreateFisrtProject,
            ipbtenCreateNewProject, tvProjectList
        ).forEach {
            it.isVisible = false
        }
        layoutErrorScreen.isVisible = true
    }

    private fun fetchErrorScreen(errorState: ErrorScreenState) {
        val errorImageRes = errorState.imageResource
        val errorTextRes = errorState.messageResource
        binding.layoutErrorScreen.apply {
            findViewById<ImageView>(com.innoprog.android.uikit.R.id.iv_error_image)
                .setImageResource(errorImageRes)
            findViewById<TextView>(com.innoprog.android.uikit.R.id.tv_error_message)
                .setText(errorTextRes)
        }
    }
}
