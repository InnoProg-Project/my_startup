package com.innoprog.android.feature.auth.newpassword.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [NewPasswordModule::class]
)
interface NewPasswordComponent : ScreenComponent
