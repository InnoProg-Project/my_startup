package com.innoprog.android.feature.feed.userprojectscreen.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [UserProjectDetailsModule::class]
)
interface UserProjectDetailsComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): UserProjectDetailsComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}