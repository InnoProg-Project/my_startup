package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.base.ViewModelSample
import com.innoprog.android.databinding.FragmentCodeEntryBinding

class CodeEntryFragment : BaseFragment<FragmentCodeEntryBinding>() {
    override val viewModel: BaseViewModel by viewModels<ViewModelSample>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeEntryBinding {
        return FragmentCodeEntryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.navigateUp()
        }
    }
}
