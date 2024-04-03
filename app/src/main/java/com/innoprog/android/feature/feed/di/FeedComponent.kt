package com.innoprog.android.feature.feed.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [FeedModule::class]
)

interface FeedComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): FeedComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
