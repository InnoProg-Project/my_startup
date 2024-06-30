package com.innoprog.android.feature.training.courseInformation.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.courseInformation.data.CourseInformationRepositoryImpl
import com.innoprog.android.feature.training.courseInformation.data.network.CourseInformationApi
import com.innoprog.android.feature.training.courseInformation.data.network.NetworkClient
import com.innoprog.android.feature.training.courseInformation.data.network.RetrofitNetworkClient
import com.innoprog.android.feature.training.courseInformation.domain.CourseInformationRepository
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCase
import com.innoprog.android.feature.training.courseInformation.domain.useCase.GetCourseInformationUseCaseImpl
import com.innoprog.android.feature.training.courseInformation.presentation.CourseInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [CourseInformationModule.CourseInformationApiModule::class])
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

    @Binds
    fun bindNetworkClient(impl: RetrofitNetworkClient): NetworkClient

    @Module
    class CourseInformationApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): CourseInformationApi {
            return retrofit.create(CourseInformationApi::class.java)
        }
    }
}
