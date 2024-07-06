package com.innoprog.android.feature.feed.userprojectscreen.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.userprojectscreen.presentation.UserProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserProjectDetailsModule {

    @IntoMap
    @ViewModelKey(UserProjectViewModel::class)
    @Binds
    fun bindUserProjectViewModel(viewModel: UserProjectViewModel): ViewModel
}