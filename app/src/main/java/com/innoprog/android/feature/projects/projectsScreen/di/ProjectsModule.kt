package com.innoprog.android.feature.projects.projectsScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.projectsScreen.presentation.ProjectsScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProjectsModule {

    @IntoMap
    @ViewModelKey(ProjectsScreenViewModel::class)
    @Binds
    fun bindProjectsScreenViewModel(impl: ProjectsScreenViewModel): ViewModel
}
