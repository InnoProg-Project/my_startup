package com.innoprog.android.feature.projects.create.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.create.presentation.FillAboutProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FillAboutProjectModule {
    @Binds
    @ViewModelKey(FillAboutProjectViewModel::class)
    @IntoMap
    fun bindViewModel(viewModel: FillAboutProjectViewModel): ViewModel
}
