package com.innoprog.android.feature.feed.anyProjectDetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.anyProjectDetails.data.AnyProjectDetailsRepositoryImpl
import com.innoprog.android.feature.feed.anyProjectDetails.data.network.AnyProjectDetailsApi
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsInteractor
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsInteractorImpl
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsRepository
import com.innoprog.android.feature.feed.anyProjectDetails.presentation.AnyProjectDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [AnyProjectDetailsModule.AnyProjectDetailsApiModule::class])
interface AnyProjectDetailsModule {
    @IntoMap
    @ViewModelKey(AnyProjectDetailsViewModel::class)
    @Binds
    fun bindAnyProjectDetailsViewModel(impl: AnyProjectDetailsViewModel): ViewModel

    @Binds
    fun bindAnyProjectDetailsRepository(
        anyProjectDetailsRepositoryImpl: AnyProjectDetailsRepositoryImpl
    ): AnyProjectDetailsRepository

    @Binds
    fun bindAnyProjectDetailsInteractor(
        anyProjectDetailsInteractorImpl: AnyProjectDetailsInteractorImpl
    ): AnyProjectDetailsInteractor

    @Module
    class AnyProjectDetailsApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): AnyProjectDetailsApi {
            return retrofit.create(AnyProjectDetailsApi::class.java)
        }
    }
}
