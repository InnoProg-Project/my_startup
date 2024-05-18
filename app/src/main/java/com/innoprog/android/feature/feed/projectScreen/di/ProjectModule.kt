package com.innoprog.android.feature.feed.projectScreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.projectScreen.presentation.ProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProjectModule {
    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    @Binds
    fun bindProjectViewModel(impl: ProjectViewModel): ViewModel
}
