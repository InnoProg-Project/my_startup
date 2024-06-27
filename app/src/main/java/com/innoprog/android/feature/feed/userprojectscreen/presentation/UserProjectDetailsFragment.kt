package com.innoprog.android.feature.feed.userprojectscreen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.databinding.FragmentUserProjectDetailsBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.feed.userprojectscreen.di.DaggerUserProjectComponent

class UserProjectDetailsFragment :
    BaseFragment<FragmentUserProjectDetailsBinding, UserProjectViewModel>() {

    override val viewModel by injectViewModel<UserProjectViewModel>()
    override fun diComponent(): ScreenComponent = DaggerUserProjectComponent.builder().build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserProjectDetailsBinding {
        return FragmentUserProjectDetailsBinding.inflate(inflater, container, false)
    }


}