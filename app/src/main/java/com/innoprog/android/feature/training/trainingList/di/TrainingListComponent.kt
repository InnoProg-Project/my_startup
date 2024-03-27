package com.innoprog.android.feature.training.trainingList.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [TrainingListModule::class]
)
interface TrainingListComponent : ScreenComponent
