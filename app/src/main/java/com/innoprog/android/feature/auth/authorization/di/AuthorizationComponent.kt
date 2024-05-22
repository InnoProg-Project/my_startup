package com.innoprog.android.feature.auth.authorization.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [AuthorizationModule::class]
)
interface AuthorizationComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): AuthorizationComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
