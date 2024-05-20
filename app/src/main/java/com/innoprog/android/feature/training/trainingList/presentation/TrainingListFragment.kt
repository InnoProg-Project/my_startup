package com.innoprog.android.feature.training.trainingList.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentTrainingListBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.common.VerticalSpaceDecorator
import com.innoprog.android.feature.training.trainingList.di.DaggerTrainingListComponent
import com.innoprog.android.uikit.R

class TrainingListFragment : BaseFragment<FragmentTrainingListBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<TrainingListViewModel>()
    private val trainingAdapter = TrainingRecyclerViewAdapter { courseId ->
        viewModel.navigateTo(
            com.innoprog.android.R.id.courseInformationFragment,
            bundleOf(COURSE_KEY to courseId)
        )
    }
    override fun diComponent(): ScreenComponent = DaggerTrainingListComponent.builder().build()

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
            is TrainingListState.Load, TrainingListState.Error -> {
                binding.trainingRecyclerView.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.VISIBLE
            }

            is TrainingListState.Content -> {
                trainingAdapter.items = state.trainingList
                binding.trainingRecyclerView.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        const val COURSE_KEY = "COURSE_KEY"
    }
}
