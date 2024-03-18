package com.innoprog.android.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.base.ViewModelSample
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ViewModelSample::class)
    @Binds
    fun bindBaseViewModel(impl: ViewModelSample): ViewModel
}
