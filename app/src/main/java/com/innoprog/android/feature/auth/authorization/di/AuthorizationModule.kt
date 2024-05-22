package com.innoprog.android.feature.auth.authorization.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.authorization.data.AuthorisationRepositoryImpl
import com.innoprog.android.feature.auth.authorization.data.network.LoginApi
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationRepository
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCase
import com.innoprog.android.feature.auth.authorization.domain.AuthorisationUseCaseImpl
import com.innoprog.android.feature.auth.authorization.presentation.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [AuthorizationModule.LoginApiModule::class])
interface AuthorizationModule {

    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    @Binds
    fun bindAuthorizationViewModel(impl: AuthorizationViewModel): ViewModel

    @Binds
    fun provideAuthorisationUseCase(useCase: AuthorisationUseCaseImpl): AuthorisationUseCase

    @Binds
    fun provideAuthorisationRepository(repository: AuthorisationRepositoryImpl): AuthorisationRepository


    @Module
    class LoginApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): LoginApi {
            return retrofit.create(LoginApi::class.java)
        }
    }
}
