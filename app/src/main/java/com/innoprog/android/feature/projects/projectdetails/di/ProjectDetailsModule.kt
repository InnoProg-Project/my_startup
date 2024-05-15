package com.innoprog.android.feature.projects.projectdetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.projectdetails.presentation.ProjectDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProjectDetailsModule {

    @IntoMap
    @ViewModelKey(ProjectDetailsViewModel::class)
    @Binds
    fun bindProjectViewModel(impl: ProjectDetailsViewModel): ViewModel
}
