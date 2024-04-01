package com.innoprog.android.feature.profile.aboutapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentAboutAppBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.aboutapp.di.DaggerAboutAppComponent

class AboutAppFragment : BaseFragment<FragmentAboutAppBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<AboutAppViewModel>()

    override fun diComponent(): ScreenComponent = DaggerAboutAppComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutAppBinding {
        return FragmentAboutAppBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopBar()
    }

    private fun initTopBar() {
        binding.topbarAboutApp.setRightIconVisibility()
        binding.topbarAboutApp.setLeftIconClickListener {
            viewModel.navigateUp()
        }
    }
}
