package com.innoprog.android.feature.auth.authorization.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [AuthorizationModule::class]
)
interface AuthorizationComponent : ScreenComponent
