package com.innoprog.android.feature.feed.anyProjectDetails.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [AnyProjectDetailsModule::class]
)
interface AnyProjectDetailsComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): AnyProjectDetailsComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
