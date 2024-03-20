package com.innoprog.android.feature.training.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentTrainingBinding
import com.innoprog.android.di.DaggerAppComponent

class TrainingFragment : BaseFragment<FragmentTrainingBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<TrainingViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingBinding {
        return FragmentTrainingBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@TrainingFragment)
        super.onCreate(savedInstanceState)
    }
}
