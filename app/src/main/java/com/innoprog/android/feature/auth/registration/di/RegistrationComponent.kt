package com.innoprog.android.feature.auth.registration.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RegistrationModule::class]
)
interface RegistrationComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): RegistrationComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
