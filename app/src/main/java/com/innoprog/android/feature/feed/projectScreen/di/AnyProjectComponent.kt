package com.innoprog.android.feature.feed.projectScreen.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [AppComponent::class], modules = [AnyProjectModule::class])
interface AnyProjectComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun build(): AnyProjectComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
