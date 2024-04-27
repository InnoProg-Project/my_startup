package com.innoprog.android.feature.profile.editingprofile.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.network.data.NetworkModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [EditingProfileModule::class, NetworkModule::class]
)
interface EditingProfileComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): EditingProfileComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
