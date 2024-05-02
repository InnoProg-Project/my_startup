package com.innoprog.android.feature.projects.uploadPhotoAndVideo.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.uploadPhotoAndVideo.presentation.UploadPhotoAndVideoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UploadPhotoAndVideoModule {

    @IntoMap
    @ViewModelKey(UploadPhotoAndVideoViewModel::class)
    @Binds
    fun bindProjectsViewModel(impl: UploadPhotoAndVideoViewModel): ViewModel
}
