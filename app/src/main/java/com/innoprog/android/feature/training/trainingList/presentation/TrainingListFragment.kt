package com.innoprog.android.feature.training.trainingList.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentTrainingListBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.trainingList.di.DaggerTrainingListComponent
import com.innoprog.android.uikit.R

class TrainingListFragment : BaseFragment<FragmentTrainingListBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<TrainingListViewModel>()
    private val trainingAdapter = TrainingRecyclerViewAdapter { courseId ->
        val direction =
            TrainingListFragmentDirections.actionTrainingListFragmentToCourseInformationFragment(
                courseId
            )
        viewModel.navigateTo(direction)
    }

    override fun diComponent(): ScreenComponent {
        return DaggerTrainingListComponent
            .builder()
            .appComponent(AppComponentHolder.getComponent())
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingListBinding {
        return FragmentTrainingListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val decorator = VerticalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
        binding.trainingRecyclerView.addItemDecoration(decorator)
        binding.trainingRecyclerView.adapter = trainingAdapter
    }

    private fun render(state: TrainingListState) {
        when (state) {
            is TrainingListState.EmptyList -> {
                hideProgress()
                binding.trainingRecyclerView.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.VISIBLE
            }

            is TrainingListState.Error -> {
                hideProgress()
                binding.trainingRecyclerView.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.VISIBLE
            }

            is TrainingListState.UnAuthorisedError -> {
                viewModel.navigateToStart()
            }

            is TrainingListState.Content -> {
                hideProgress()
                trainingAdapter.items = state.trainingList
                trainingAdapter.notifyDataSetChanged()
                binding.trainingRecyclerView.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.INVISIBLE
            }

            is TrainingListState.Load -> {
                binding.trainingRecyclerView.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.INVISIBLE
                showProgress()
            }
        }
    }

    private fun showProgress() {
        binding.progress.show()
        binding.lblProgressDescription.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.hide()
        binding.lblProgressDescription.visibility = View.GONE
    }
}
