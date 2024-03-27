package com.innoprog.android.feature.training.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.data.TrainingListRepositoryImpl
import com.innoprog.android.feature.training.domain.TrainingListRepository
import com.innoprog.android.feature.training.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.domain.useCase.GetTrainingListUseCaseImpl
import com.innoprog.android.feature.training.presentation.TrainingListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TrainingListModule {

    @IntoMap
    @ViewModelKey(TrainingListViewModel::class)
    @Binds
    fun bindTrainingListViewModel(impl: TrainingListViewModel): ViewModel

    @Binds
    fun provideTrainingListRepository(trainingListRepositoryImpl: TrainingListRepositoryImpl): TrainingListRepository

    @Binds
    fun provideGetTrainingListUseCase(getTrainingListUseCaseImpl: GetTrainingListUseCaseImpl): GetTrainingListUseCase
}
