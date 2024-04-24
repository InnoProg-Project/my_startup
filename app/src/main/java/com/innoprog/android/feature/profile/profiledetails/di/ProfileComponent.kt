package com.innoprog.android.feature.profile.profiledetails.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.network.data.NetworkModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ProfileModule::class, NetworkModule::class]
)
interface ProfileComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun build(): ProfileComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
