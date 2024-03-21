package com.innoprog.android.feature.auth.codeentry.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [CodeEntryModule::class]
)
interface CodeEntryComponent : ScreenComponent
