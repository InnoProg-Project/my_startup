package com.innoprog.android.feature.feed.newsfeed.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsfeed.data.FeedRepositoryImpl
import com.innoprog.android.feature.feed.newsfeed.data.network.FeedApi
import com.innoprog.android.feature.feed.newsfeed.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsfeed.data.network.RetrofitNetworkClient
import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.FeedRepository
import com.innoprog.android.feature.feed.newsfeed.domain.impl.FeedInteractorImpl
import com.innoprog.android.feature.feed.newsfeed.presentation.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [FeedModule.FeedApiModule::class])
interface FeedModule {
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: FeedViewModel): ViewModel

    @Binds
    fun bindFeedRepository(feedRepositoryImpl: FeedRepositoryImpl): FeedRepository

    @Binds
    fun bindFeedInteractor(feedInteractorImpl: FeedInteractorImpl): FeedInteractor

    @Binds
    fun bindNetworkClient(impl: RetrofitNetworkClient): NetworkClient

    @Module
    class FeedApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): FeedApi {
            return retrofit.create(FeedApi::class.java)
        }
    }
}
