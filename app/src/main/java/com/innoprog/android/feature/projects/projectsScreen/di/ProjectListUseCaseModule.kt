package com.innoprog.android.feature.projects.projectsScreen.di

import com.innoprog.android.feature.projects.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.feature.projects.projectsScreen.domain.impl.GetProjectListUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProjectListUseCaseModule {

    @Provides
    fun provideGetProjectListUseCase(
        repository: ProjectRepository
    ): GetProjectListUseCase = GetProjectListUseCaseImpl(repository)
}