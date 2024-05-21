package com.innoprog.android.feature.feed.projectScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.projectScreen.data.AnyProjectRepositoryImpl
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectInteractor
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectInteractorImpl
import com.innoprog.android.feature.feed.projectScreen.domain.AnyProjectRepository
import com.innoprog.android.feature.feed.projectScreen.presentation.AnyProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AnyProjectModule {
    @IntoMap
    @ViewModelKey(AnyProjectViewModel::class)
    @Binds
    fun bindAnyProjectViewModel(impl: AnyProjectViewModel): ViewModel

    @Binds
    fun bindAnyProjectRepository(anyProjectRepositoryImpl: AnyProjectRepositoryImpl): AnyProjectRepository

    @Binds
    fun bindAnyProjectInteractor(anyProjectInteractorImpl: AnyProjectInteractorImpl): AnyProjectInteractor
}
