package com.innoprog.android.feature.projects.create.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.auth.registration.di.RegistrationComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CreateProjectModule::class]
)
interface CreateProjectComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): RegistrationComponent
        fun appComponent(appComponent: AppComponent): RegistrationComponent.Builder
    }
}
