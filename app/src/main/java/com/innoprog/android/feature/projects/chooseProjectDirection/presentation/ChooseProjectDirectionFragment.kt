package com.innoprog.android.feature.projects.chooseProjectDirection.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditProjectDirectionBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.chooseProjectDirection.di.DaggerChooseProjectDirectionComponent
import com.innoprog.android.feature.projects.chooseProjectDirection.domain.model.ProjectDirectionModel

class ChooseProjectDirectionFragment : BaseFragment<FragmentEditProjectDirectionBinding, BaseViewModel>() {

    private val adapter by lazy { ChooseProjectDirectionAdapter() }

    override val viewModel by injectViewModel<ChooseProjectDirectionViewModel>()
    override fun diComponent(): ScreenComponent = DaggerChooseProjectDirectionComponent.builder().build()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentEditProjectDirectionBinding {
        return FragmentEditProjectDirectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Тестовый набор данных
        val itemSample = ProjectDirectionModel("test")
        val listSample = mutableListOf(itemSample, itemSample, itemSample, itemSample, itemSample)
        adapter.items = listSample
        binding.chooseProjectDirectionRV.adapter = adapter
    }
}