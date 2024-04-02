package com.innoprog.android.feature.training.courseInformation.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.courseInformation.data.CourseInformationRepositoryImpl
import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCaseImpl
import com.innoprog.android.feature.training.courseInformation.presentation.CourseInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CourseInformationModule {

    @IntoMap
    @ViewModelKey(CourseInformationViewModel::class)
    @Binds
    fun bindCourseInformationViewModel(impl: CourseInformationViewModel): ViewModel

    @Binds
    fun provideCourseInformationRepository(
        courseInformationRepositoryImpl: CourseInformationRepositoryImpl
    ): CourseInformationRepository

    @Binds
    fun provideGetCourseInformationUseCase(
        getCourseInformationUseCaseImpl: GetCourseInformationUseCaseImpl
    ): GetCourseInformationUseCase
}
