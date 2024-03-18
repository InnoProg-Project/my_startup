package com.innoprog.android.feature.training.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentTrainingBinding
import com.innoprog.android.base.ViewModelSample

class TrainingFragment : BaseFragment<FragmentTrainingBinding>() {
    override val viewModel: BaseViewModel by viewModels<ViewModelSample>()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingBinding {
        return FragmentTrainingBinding.inflate(inflater, container, false)
    }
}
