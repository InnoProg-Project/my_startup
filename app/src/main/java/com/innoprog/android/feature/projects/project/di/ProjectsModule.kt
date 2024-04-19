package com.innoprog.android.feature.projects.project.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.project.presentation.ProjectsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProjectsModule {

    @IntoMap
    @ViewModelKey(ProjectsViewModel::class)
    @Binds
    fun bindProjectsViewModel(impl: ProjectsViewModel): ViewModel
}