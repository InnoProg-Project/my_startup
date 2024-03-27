package com.innoprog.android.feature.training.course_information.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [CourseInformationModule::class]
)
interface CourseInformationComponent : ScreenComponent
