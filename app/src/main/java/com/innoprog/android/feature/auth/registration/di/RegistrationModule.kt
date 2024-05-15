package com.innoprog.android.feature.auth.registration.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.registration.data.RegistrationRepositoryImpl
import com.innoprog.android.feature.auth.registration.domain.RegistrationRepository
import com.innoprog.android.feature.auth.registration.domain.RegistrationUseCase
import com.innoprog.android.feature.auth.registration.domain.RegistrationUseCaseImpl
import com.innoprog.android.feature.auth.registration.presentation.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegistrationModule {

    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    @Binds
    fun bindNewRegistrationViewModel(impl: RegistrationViewModel): ViewModel

    @Binds
    fun provideRegistrationUseCase(useCase: RegistrationUseCaseImpl): RegistrationUseCase

    @Binds
    fun provideRegistrationRepository(repository: RegistrationRepositoryImpl): RegistrationRepository
}
