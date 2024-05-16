package com.innoprog.android.feature.edit.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.network.data.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppComponent::class],
    modules = [CreateEditContentModule::class, NetworkModule::class]
)
interface CreateEditContentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): CreateEditContentComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
