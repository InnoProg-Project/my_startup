package com.innoprog.android.feature.projects.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [EditingDocksModule::class]
)
interface EditingDocksComponent : ScreenComponent
