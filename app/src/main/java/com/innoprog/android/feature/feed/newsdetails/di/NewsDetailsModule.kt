package com.innoprog.android.feature.feed.newsdetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsdetails.data.NewsDetailsRepositoryImpl
import com.innoprog.android.feature.feed.newsdetails.data.network.NewsDetailsApi
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractorImpl
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsdetails.presentation.NewsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [NewsDetailsModule.NewsDetailsApiModule::class])
interface NewsDetailsModule {
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    @Binds
    fun bindNewsDetailsViewModel(impl: NewsDetailsViewModel): ViewModel

    @Binds
    fun bindNewsDetailsRepository(newsDetailsRepositoryImpl: NewsDetailsRepositoryImpl): NewsDetailsRepository

    @Binds
    fun bindNewsDetailsInteractor(newsDetailsInteractorImpl: NewsDetailsInteractorImpl): NewsDetailsInteractor

    @Module
    class NewsDetailsApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): NewsDetailsApi {
            return retrofit.create(NewsDetailsApi::class.java)
        }
    }
}
