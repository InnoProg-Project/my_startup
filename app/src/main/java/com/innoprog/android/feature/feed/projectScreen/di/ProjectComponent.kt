package com.innoprog.android.feature.feed.projectScreen.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [ProjectModule::class]
)

interface ProjectComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): ProjectComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
