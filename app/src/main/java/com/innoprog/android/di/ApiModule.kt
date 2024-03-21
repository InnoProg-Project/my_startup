package com.innoprog.android.di

import com.innoprog.android.data.network.ApiService
import com.innoprog.android.domain.ApiInteractor
import com.innoprog.android.domain.ApiInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideApiInteractor(apiService: ApiService): ApiInteractor {
        return ApiInteractorImpl(apiService)
    }
}