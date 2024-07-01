package com.innoprog.android.feature.training.courseInformation.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [CourseInformationModule::class]
)
interface CourseInformationComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): CourseInformationComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
