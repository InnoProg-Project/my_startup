package com.innoprog.android.feature.training.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.data.TrainingRepositoryImpl
import com.innoprog.android.feature.training.domain.TrainingRepository
import com.innoprog.android.feature.training.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.domain.useCase.GetTrainingListUseCaseImpl
import com.innoprog.android.feature.training.presentation.TrainingViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class TrainingModule {

    @Provides
    fun provideTrainingRepository(): TrainingRepository {
        return TrainingRepositoryImpl()
    }

    @Provides
    fun provideGetTrainingListUseCase(trainingRepository: TrainingRepository): GetTrainingListUseCase {
        return GetTrainingListUseCaseImpl(trainingRepository)
    }
}

@Module
interface AbstractTrainingModule {

    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    @Binds
    fun bindTrainingViewModel(impl: TrainingViewModel): ViewModel
}
