package com.innoprog.android.feature.edit.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CreateEditContentModule::class]
)
interface CreateEditContentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): CreateEditContentComponent
    }
}
