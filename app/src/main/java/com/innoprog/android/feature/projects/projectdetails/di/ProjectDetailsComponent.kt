package com.innoprog.android.feature.projects.projectdetails.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ProjectDetailsModule::class]
)
interface ProjectDetailsComponent : ScreenComponent
