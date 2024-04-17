package com.innoprog.android.feature.auth.authorization.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.network.data.NetworkModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [AuthorizationModule::class, NetworkModule::class]
)
interface AuthorizationComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): AuthorizationComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
