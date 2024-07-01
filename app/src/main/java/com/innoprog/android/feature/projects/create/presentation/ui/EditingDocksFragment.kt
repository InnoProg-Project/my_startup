package com.innoprog.android.feature.projects.create.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProjectAttachDocksBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.create.editingdocks.DocumentsAdapter
import com.innoprog.android.feature.projects.create.editingdocks.EditingDocksViewModel
import com.innoprog.android.feature.projects.project.di.DaggerEditingDocksComponent

class EditingDocksFragment : BaseFragment<FragmentProjectAttachDocksBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<EditingDocksViewModel>()
    override fun diComponent(): ScreenComponent {
        TODO("Not yet implemented")
    }

    private var documentAdapter: DocumentsAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectAttachDocksBinding {
        return FragmentProjectAttachDocksBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopBar()
        initRecycler()
        binding.btResume.setOnClickListener {
            viewModel.navigateTo(R.id.action_editingDocksFragment_to_additionalInformationFragment)
        }
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
