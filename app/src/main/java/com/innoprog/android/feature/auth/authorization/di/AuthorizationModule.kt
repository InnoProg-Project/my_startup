package com.innoprog.android.feature.auth.authorization.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.authorization.data.AuthorisationRepositoryImpl
import com.innoprog.android.feature.auth.authorization.data.NetworkClient
import com.innoprog.android.feature.auth.authorization.data.RetrofitNetworkClient
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCaseImpl
import com.innoprog.android.feature.auth.authorization.presentation.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthorizationModule {

    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    @Binds
    fun bindAuthorizationViewModel(impl: AuthorizationViewModel): ViewModel

    @Binds
    fun provideAuthorisationUseCase(useCase: AuthorisationUseCaseImpl): AuthorisationUseCase

    @Binds
    fun provideAuthorisationRepository(repository: AuthorisationRepositoryImpl): AuthorisationRepository

    @Binds
    fun provideNetworkClient(retrofit: RetrofitNetworkClient): NetworkClient
}
