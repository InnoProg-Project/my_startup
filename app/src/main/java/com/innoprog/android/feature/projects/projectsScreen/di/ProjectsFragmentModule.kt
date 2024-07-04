package com.innoprog.android.feature.projects.projectsScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.db.RoomDB
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.profiledetails.data.db.ProfileDao
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectApiService
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListNetworkClient
import com.innoprog.android.feature.projects.projectsScreen.data.network.ProjectListNetworkClientImpl
import com.innoprog.android.feature.projects.projectsScreen.data.repository.ProjectRepositoryImpl
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.feature.projects.projectsScreen.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.projectsScreen.domain.impl.GetProjectListUseCaseImpl
import com.innoprog.android.feature.projects.projectsScreen.presentation.ProjectsScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [ProjectsFragmentModule.ProjectApiModule::class])
interface ProjectsFragmentModule {

    @IntoMap
    @ViewModelKey(ProjectsScreenViewModel::class)
    @Binds
    fun bindVM(impl: ProjectsScreenViewModel): ViewModel

    @Binds
    fun bindNetworkCliend(impl: ProjectListNetworkClientImpl): ProjectListNetworkClient

    @Binds
    fun bindRepository(impl: ProjectRepositoryImpl): ProjectRepository

    @Binds
    fun bindProjectListUseCase(impl: GetProjectListUseCaseImpl): GetProjectListUseCase

    @Module
    class ProjectApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): ProjectApiService {
            return retrofit.create(ProjectApiService::class.java)
        }

        @Provides
        fun provideProfileDao(database: RoomDB): ProfileDao {
            return database.profileDao()
        }
    }
}
