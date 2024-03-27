package com.innoprog.android.feature.training.course_information.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.course_information.data.CourseInformationRepositoryImpl
import com.innoprog.android.feature.training.course_information.domain.CourseInformationRepository
import com.innoprog.android.feature.training.course_information.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.feature.training.course_information.domain.useCase.GetCourseInformationUseCaseImpl
import com.innoprog.android.feature.training.course_information.presentation.CourseInformationViewModel
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
    fun provideCourseInformationRepository(courseInformationRepositoryImpl: CourseInformationRepositoryImpl): CourseInformationRepository

    @Binds
    fun provideGetCourseInformationUseCase(getCourseInformationUseCaseImpl: GetCourseInformationUseCaseImpl): GetCourseInformationUseCase
}
