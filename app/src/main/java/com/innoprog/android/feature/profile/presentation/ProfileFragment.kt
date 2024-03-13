package com.innoprog.android.feature.profile.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }
}
