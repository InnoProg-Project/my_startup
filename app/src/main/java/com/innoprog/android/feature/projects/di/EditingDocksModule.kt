package com.innoprog.android.feature.projects.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.presentation.editingdocks.EditingDocksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditingDocksModule {

    @IntoMap
    @ViewModelKey(EditingDocksViewModel::class)
    @Binds
    fun bindProjectsViewModel(impl: EditingDocksViewModel): ViewModel
}
