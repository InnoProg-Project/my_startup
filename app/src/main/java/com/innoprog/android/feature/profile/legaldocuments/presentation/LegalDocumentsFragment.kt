package com.innoprog.android.feature.profile.legaldocuments.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.R
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentLegalDocumentsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.legaldocuments.di.DaggerLegalDocumentComponent

class LegalDocumentsFragment : BaseFragment<FragmentLegalDocumentsBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<LegalDocumentViewModel>()
    override fun diComponent(): ScreenComponent = DaggerLegalDocumentComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLegalDocumentsBinding {
        return FragmentLegalDocumentsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationToDocs()
        initTopBar()
    }

    private fun navigationToDocs() {
        binding.licenseAgreement.setOnClickListener {
            navigation()
        }

        binding.personalInformation.setOnClickListener {
            navigation()
        }
    }

    private fun navigation() {
        viewModel.navigateTo(R.id.documentFragment)
    }

    private fun initTopBar() {
        binding.topbar.setRightIconVisibility()
        binding.topbar.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
