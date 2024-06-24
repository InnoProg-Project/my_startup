package com.innoprog.android.feature.projects.projectsScreen.di

import com.innoprog.android.feature.auth.authorization.data.network.LoginApi
import com.innoprog.android.feature.projects.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.projectsScreen.data.api.ProjectApiService
import com.innoprog.android.feature.projects.projectsScreen.data.repository.ProjectRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProjectListDataModule {
    @Provides
    fun provideProjectRepository(
        api: ProjectApiService
    ): ProjectRepository = ProjectRepositoryImpl(api)

    @Module
    class ProjectListApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): ProjectApiService {
            return retrofit.create(ProjectApiService::class.java)
        }
    }
}