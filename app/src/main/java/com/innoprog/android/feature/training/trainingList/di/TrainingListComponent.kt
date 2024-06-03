package com.innoprog.android.feature.training.trainingList.di

import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.profile.profiledetails.di.ProfileComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [TrainingListModule::class]
)
interface TrainingListComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): TrainingListComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
