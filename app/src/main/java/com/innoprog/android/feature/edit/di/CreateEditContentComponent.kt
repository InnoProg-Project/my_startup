package com.innoprog.android.feature.edit.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [CreateEditContentModule::class]
)
interface CreateEditContentComponent : ScreenComponent
