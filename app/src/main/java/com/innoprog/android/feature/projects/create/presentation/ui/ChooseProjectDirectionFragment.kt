package com.innoprog.android.feature.projects.create.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.innoprog.android.feature.projects.create.domain.model.ProjectDirectionModel
import com.innoprog.android.feature.projects.create.presentation.ui.adapter.ChooseProjectDirectionAdapter

class ChooseProjectDirectionFragment : Fragment() {

    private val adapter by lazy { ChooseProjectDirectionAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Тестовый набор данных
        val itemSample = ProjectDirectionModel("test")
        val listSample = mutableListOf(itemSample, itemSample, itemSample, itemSample, itemSample)
        adapter.items = listSample
        binding.chooseProjectDirectionRV.adapter = adapter
    }
}
