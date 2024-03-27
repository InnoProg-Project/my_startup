package com.innoprog.android.feature.training.courseInformation.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [CourseInformationModule::class]
)
interface CourseInformationComponent : ScreenComponent
