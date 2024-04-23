package com.innoprog.android.feature.projects.projectsScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.projects.projectsScreen.data.ProjectsScreenRepositoryImpl
import com.innoprog.android.feature.projects.projectsScreen.domain.ProjectsScreenRepository
import com.innoprog.android.feature.projects.projectsScreen.domain.useCase.GetProjectsUseCase
import com.innoprog.android.feature.projects.projectsScreen.domain.useCase.impl.GetProjectsUseCaseImpl
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

    @Binds
    fun provideSearchScreenRepository(
        impl: ProjectsScreenRepositoryImpl
    ): ProjectsScreenRepository

    @Binds
    fun provideGetProjectsUseCase(
        impl: GetProjectsUseCaseImpl
    ): GetProjectsUseCase
}
