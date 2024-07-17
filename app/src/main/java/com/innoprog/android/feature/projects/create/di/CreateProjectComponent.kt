package com.innoprog.android.feature.projects.create.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CreateProjectModule::class]
)
interface CreateProjectComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): CreateProjectComponent
        fun appComponent(appComponent: AppComponent): CreateProjectComponent.Builder
    }
}
