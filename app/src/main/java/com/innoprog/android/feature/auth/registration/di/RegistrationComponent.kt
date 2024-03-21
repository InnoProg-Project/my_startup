package com.innoprog.android.feature.auth.registration.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [RegistrationModule::class]
)
interface RegistrationComponent : ScreenComponent
