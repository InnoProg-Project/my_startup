package com.innoprog.android.feature.edit.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.edit.presentation.CreateEditContentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CreateEditContentModule {
    @IntoMap
    @ViewModelKey(CreateEditContentViewModel::class)
    @Binds
    fun bindCreateEditContentViewModel(impl: CreateEditContentViewModel): ViewModel
}
