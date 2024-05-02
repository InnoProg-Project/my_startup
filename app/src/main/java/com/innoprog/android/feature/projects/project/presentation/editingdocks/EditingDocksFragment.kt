package com.innoprog.android.feature.projects.project.presentation.editingdocks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentEditingDocksBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.project.di.DaggerEditingDocksComponent

class EditingDocksFragment : BaseFragment<FragmentEditingDocksBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<EditingDocksViewModel>()
    override fun diComponent(): ScreenComponent = DaggerEditingDocksComponent.builder().build()

    private var documentAdapter: DocumentsAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditingDocksBinding {
        return FragmentEditingDocksBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopBar()

        initRecycler()
    }

    private fun initTopBar() {
        binding.topbarEditingDocks.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }

    private fun initRecycler() {
        documentAdapter = DocumentsAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.docksRecycler.adapter = documentAdapter
    }
}
