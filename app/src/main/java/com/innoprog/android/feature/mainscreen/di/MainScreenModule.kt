package com.innoprog.android.feature.mainscreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.mainscreen.presentation.MainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainScreenModule {

    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    @Binds
    fun bindMainScreenViewModel(impl: MainScreenViewModel): ViewModel
}
