package com.innoprog.android.feature.profile.documents.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [DocumentModule::class]
)
interface DocumentComponent : ScreenComponent
