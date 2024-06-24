package com.innoprog.android.feature.projects.projectsScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.projectsScreen.presentation.ProjectsScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProjectsFragmentModule {

    @IntoMap
    @ViewModelKey(ProjectsScreenViewModel::class)
    @Binds
    fun bindVM(impl: ProjectsScreenViewModel): ViewModel
}
