package com.innoprog.android.network.data

import com.innoprog.android.network.domain.ApiInteractor
import com.innoprog.android.network.domain.ApiInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface ApiModule {

    @Binds
    fun provideApiInteractor(impl: ApiInteractorImpl): ApiInteractor
}
