package com.innoprog.android.feature.training.training_list.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.training_list.data.TrainingListRepositoryImpl
import com.innoprog.android.feature.training.training_list.domain.TrainingListRepository
import com.innoprog.android.feature.training.training_list.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.training_list.domain.useCase.GetTrainingListUseCaseImpl
import com.innoprog.android.feature.training.training_list.presentation.TrainingListViewModel
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
