package com.innoprog.android.feature.projects.projectsScreen.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ProjectsModule::class]
)
interface ProjectsComponent : ScreenComponent
