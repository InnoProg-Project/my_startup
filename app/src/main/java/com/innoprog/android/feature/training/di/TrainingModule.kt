package com.innoprog.android.feature.training.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.presentation.TrainingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TrainingModule {

    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    @Binds
    fun bindTrainingViewModel(impl: TrainingViewModel): ViewModel
}
