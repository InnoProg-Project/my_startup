package com.innoprog.android.feature.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.DaggerAppComponent

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProfileViewModel>()
    override fun diComponent(): AppComponent = DaggerAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        diComponent().inject(this@ProfileFragment)
        super.onCreate(savedInstanceState)
    }
}
