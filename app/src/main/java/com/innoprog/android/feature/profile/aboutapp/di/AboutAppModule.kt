package com.innoprog.android.feature.profile.aboutapp.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.aboutapp.presentation.AboutAppViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AboutAppModule {

    @IntoMap
    @ViewModelKey(AboutAppViewModel::class)
    @Binds
    fun bindAboutAppViewModel(impl: AboutAppViewModel): ViewModel
}
