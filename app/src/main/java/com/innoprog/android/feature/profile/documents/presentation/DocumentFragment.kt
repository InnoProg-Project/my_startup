package com.innoprog.android.feature.profile.documents.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentDocumentBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.documents.di.DaggerDocumentComponent

class DocumentFragment : BaseFragment<FragmentDocumentBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<DocumentViewModel>()
    override fun diComponent(): ScreenComponent = DaggerDocumentComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDocumentBinding {
        return FragmentDocumentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTopBar()
    }

    private fun initTopBar() {
        binding.topbar.setLeftIconClickListener {
            findNavController().navigateUp()
        }
    }
}
