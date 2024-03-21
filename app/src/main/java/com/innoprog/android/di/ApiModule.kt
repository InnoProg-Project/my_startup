package com.innoprog.android.di

import com.innoprog.android.domain.ApiInteractor
import com.innoprog.android.domain.ApiInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface ApiModule {

    @Binds
    fun provideApiInteractor(impl: ApiInteractorImpl): ApiInteractor
}
