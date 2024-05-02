package com.innoprog.android.feature.projects.project.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.project.presentation.AdditionalInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AdditionalInformationModule {

    @IntoMap
    @ViewModelKey(AdditionalInformationViewModel::class)
    @Binds
    fun bindAdditionalInformationViewModel(impl: AdditionalInformationViewModel): ViewModel
}
