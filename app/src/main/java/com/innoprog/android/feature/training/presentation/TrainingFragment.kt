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
import com.innoprog.android.feature.training.TrainingAdapterDecorator
import com.innoprog.android.feature.training.TrainingRecyclerViewAdapter
import com.innoprog.android.feature.training.di.DaggerTrainingComponent
import com.innoprog.android.feature.training.presentation.model.TrainingState

class TrainingFragment : BaseFragment<FragmentTrainingBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<TrainingViewModel>()
    private var trainingAdapter: TrainingRecyclerViewAdapter? = null
    override fun diComponent(): ScreenComponent = DaggerTrainingComponent.builder().build()

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
            TrainingAdapterDecorator(resources.getDimensionPixelSize(com.innoprog.android.uikit.R.dimen.margin_8))
        binding.trainingRecyclerView.addItemDecoration(decorator)
        binding.trainingRecyclerView.adapter = trainingAdapter
    }

    private fun render(state: TrainingState) {
        when (state) {
            is TrainingState.Load -> Unit
            is TrainingState.Content -> {
                trainingAdapter?.items = state.trainingList
            }

            is TrainingState.Error -> Unit
        }
    }
}
