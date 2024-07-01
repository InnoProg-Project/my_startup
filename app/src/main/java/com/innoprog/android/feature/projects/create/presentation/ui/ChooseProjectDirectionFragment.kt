package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectChooseDirectionBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.chooseProjectDirection.di.DaggerChooseProjectDirectionComponent
import com.innoprog.android.feature.projects.create.chooseProjectDirection.presentation.ChooseProjectDirectionViewModel
import com.innoprog.android.feature.projects.create.domain.ProjectDirectionModel
import com.innoprog.android.feature.projects.create.presentation.ui.adapter.ChooseProjectDirectionAdapter

class ChooseProjectDirectionFragment :
    BaseFragment<FragmentProjectChooseDirectionBinding, BaseViewModel>() {

    private val adapter by lazy { ChooseProjectDirectionAdapter() }

    override val viewModel by injectViewModel<ChooseProjectDirectionViewModel>()
    override fun diComponent(): ScreenComponent =
        DaggerChooseProjectDirectionComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectChooseDirectionBinding {
        return FragmentProjectChooseDirectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Тестовый набор данных
        val itemSample = ProjectDirectionModel("test")
        val listSample = mutableListOf(itemSample, itemSample, itemSample, itemSample, itemSample)
        adapter.items = listSample
        binding.chooseProjectDirectionRV.adapter = adapter
        binding.bvResume.setOnClickListener {
            viewModel.navigateTo(R.id.action_chooseProjectDirectionFragment_to_editingDocksFragment)
        }
    }
}
