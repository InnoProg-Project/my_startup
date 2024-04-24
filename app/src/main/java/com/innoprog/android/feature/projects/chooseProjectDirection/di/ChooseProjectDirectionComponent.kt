package com.innoprog.android.feature.projects.chooseProjectDirection.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ChooseProjectDirectionModule::class]
)
interface ChooseProjectDirectionComponent : ScreenComponent
