package com.innoprog.android.feature.projects.chooseProjectDirection.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.chooseProjectDirection.presentation.ChooseProjectDirectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ChooseProjectDirectionModule {

    @IntoMap
    @ViewModelKey(ChooseProjectDirectionViewModel::class)
    @Binds
    fun provideChooseProjectDirectionViewModel(impl: ChooseProjectDirectionViewModel): ViewModel
}
