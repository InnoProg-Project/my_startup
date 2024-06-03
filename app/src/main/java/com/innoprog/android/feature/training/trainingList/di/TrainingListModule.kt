package com.innoprog.android.feature.training.trainingList.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.trainingList.data.TrainingListRepositoryImpl
import com.innoprog.android.feature.training.trainingList.data.network.CourseApi
import com.innoprog.android.feature.training.trainingList.domain.TrainingListRepository
import com.innoprog.android.feature.training.trainingList.domain.useCase.GetTrainingListUseCase
import com.innoprog.android.feature.training.trainingList.presentation.TrainingListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(
    includes = [TrainingListModule.CourseApiModule::class]
)
interface TrainingListModule {
    @IntoMap
    @ViewModelKey(TrainingListViewModel::class)
    @Binds
    fun bindTrainingListViewModel(impl: TrainingListViewModel): ViewModel

    @Binds
    fun provideTrainingListRepository(trainingListRepositoryImpl: TrainingListRepositoryImpl): TrainingListRepository

    @Module
    class CourseApiModule {
        @Provides
        fun provideCourseApi(retrofit: Retrofit): CourseApi {
            return retrofit.create(CourseApi::class.java)
        }
    }

    val getTrainingListUseCase: GetTrainingListUseCase
}
