package com.innoprog.android.feature.projects.create.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.registration.presentation.RegistrationViewModel
import com.innoprog.android.feature.projects.create.data.CreateProjectApi
import com.innoprog.android.feature.projects.create.data.CreateProjectRepositoryImpl
import com.innoprog.android.feature.projects.create.domain.CreateProjectRepository
import com.innoprog.android.feature.projects.create.domain.CreateProjectUseCase
import com.innoprog.android.feature.projects.create.domain.impl.CreateProjectUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [CreateProjectModule.CreateProjectApiModule::class])
interface CreateProjectModule {

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    fun bindNewRegistrationViewModel(impl: RegistrationViewModel): ViewModel

    @Binds
    fun provideCreateProjectInteractor(useCase: CreateProjectUseCaseImpl): CreateProjectUseCase

    @Binds
    fun provideCreateProjectRepository(repository: CreateProjectRepositoryImpl): CreateProjectRepository

    @Module
    class CreateProjectApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): CreateProjectApi {
            return retrofit.create(CreateProjectApi::class.java)
        }
    }
}
