package com.innoprog.android.feature.feed.newsdetails.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [NewsDetailsModule::class]
)

interface NewsDetailsComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): NewsDetailsComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
