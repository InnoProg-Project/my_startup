package com.innoprog.android.feature.profile.profiledetails.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ProfileModule::class]
)
interface ProfileComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun build(): ProfileComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
