package com.innoprog.android.feature.feed.newssearch.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [NewsSearchModule::class]
)

interface NewsSearchComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): NewsSearchComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
