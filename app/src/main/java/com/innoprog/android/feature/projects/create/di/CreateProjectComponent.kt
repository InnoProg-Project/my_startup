package com.innoprog.android.feature.projects.create.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(modules = [CreateProjectModule::class])
interface CreateProjectComponent : ScreenComponent
