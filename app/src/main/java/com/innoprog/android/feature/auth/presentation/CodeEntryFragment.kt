package com.innoprog.android.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentCodeEntryBinding

class CodeEntryFragment : BaseFragment<FragmentCodeEntryBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCodeEntryBinding {
        return FragmentCodeEntryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
