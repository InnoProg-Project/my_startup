package com.innoprog.android.feature.training.training_list.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [TrainingListModule::class]
)
interface TrainingListComponent : ScreenComponent
