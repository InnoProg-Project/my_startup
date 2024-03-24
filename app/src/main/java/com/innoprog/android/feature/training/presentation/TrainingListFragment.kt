package com.innoprog.android.feature.training.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentTrainingBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.training.di.DaggerTrainingListComponent
import com.innoprog.android.feature.training.presentation.model.TrainingListState
import com.innoprog.android.uikit.R

class TrainingListFragment : BaseFragment<FragmentTrainingBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<TrainingListViewModel>()
    private var trainingAdapter: TrainingRecyclerViewAdapter? = null
    override fun diComponent(): ScreenComponent = DaggerTrainingListComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingBinding {
        return FragmentTrainingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.trainingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        trainingAdapter = TrainingRecyclerViewAdapter()
        val decorator =
            TrainingListAdapterDecorator(resources.getDimensionPixelSize(R.dimen.margin_8))
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
                trainingAdapter?.items = state.trainingList
                binding.trainingRecyclerView.visibility = View.VISIBLE
                binding.trainingNoCoursesPlaceholderIcon.visibility = View.INVISIBLE
                binding.trainingNoCoursesPlaceholderTV.visibility = View.INVISIBLE
            }
        }
    }
}
