package com.innoprog.android.feature.projects.uploadPhotoAndVideo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.innoprog.android.base.BaseFragment
import com.innoprog.android.base.BaseViewModel
import com.innoprog.android.databinding.FragmentUploadImageAndVideoBinding
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.uploadPhotoAndVideo.di.DaggerUploadPhotoAndVideoComponent

class UploadPhotoAndVideoFragment : BaseFragment<FragmentUploadImageAndVideoBinding, BaseViewModel>() {

    override val viewModel by injectViewModel<UploadPhotoAndVideoViewModel>()
    override fun diComponent(): ScreenComponent = DaggerUploadPhotoAndVideoComponent.builder().build()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentUploadImageAndVideoBinding {
        return FragmentUploadImageAndVideoBinding.inflate(inflater, container, false)
    }
}
