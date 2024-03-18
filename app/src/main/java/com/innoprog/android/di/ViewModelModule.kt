package com.innoprog.android.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.base.ViewModelSample
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("ViewModelSample")
    @Binds
    fun bindBaseViewModel(impl: ViewModelSample): ViewModel
}
