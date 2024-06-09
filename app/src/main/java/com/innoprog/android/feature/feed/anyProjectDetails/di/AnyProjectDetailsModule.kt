package com.innoprog.android.feature.feed.anyProjectDetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.anyProjectDetails.data.AnyProjectDetailsRepositoryImpl
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsInteractor
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsInteractorImpl
import com.innoprog.android.feature.feed.anyProjectDetails.domain.AnyProjectDetailsRepository
import com.innoprog.android.feature.feed.anyProjectDetails.presentation.AnyProjectDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AnyProjectDetailsModule {
    @IntoMap
    @ViewModelKey(AnyProjectDetailsViewModel::class)
    @Binds
    fun bindAnyProjectDetailsViewModel(impl: AnyProjectDetailsViewModel): ViewModel

    @Binds
    fun bindAnyProjectDetailsRepository(anyProjectDetailsRepositoryImpl: AnyProjectDetailsRepositoryImpl): AnyProjectDetailsRepository

    @Binds
    fun bindAnyProjectDetailsInteractor(anyProjectDetailsInteractorImpl: AnyProjectDetailsInteractorImpl): AnyProjectDetailsInteractor
}
