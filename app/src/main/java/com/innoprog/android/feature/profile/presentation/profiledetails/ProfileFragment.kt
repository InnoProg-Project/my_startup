package com.innoprog.android.feature.profile.presentation.profiledetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentProfileBinding
import com.innoprog.android.di.AppComponentHolder
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.di.DaggerProfileComponent
import com.innoprog.android.feature.profile.presentation.ProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, BaseViewModel>() {
    override val viewModel by injectViewModel<ProfileViewModel>()

    override fun diComponent(): ScreenComponent {
        val appComponent = AppComponentHolder.getComponent()
        return DaggerProfileComponent.builder()
            .appComponent(appComponent)
            .build()
    }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }
}
