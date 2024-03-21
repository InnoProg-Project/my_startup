package com.innoprog.android.feature.auth.passwordrecovery.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [PasswordRecoveryModule::class]
)
interface PasswordRecoveryComponent : ScreenComponent
