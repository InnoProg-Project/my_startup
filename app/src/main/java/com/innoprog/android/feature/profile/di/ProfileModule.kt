package com.innoprog.android.feature.profile.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.domain.ApiInteractor
import com.innoprog.android.domain.ApiInteractorImpl
import com.innoprog.android.feature.profile.presentation.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfileModule {

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(impl: ProfileViewModel): ViewModel

    @Binds
    fun bindApiInteractor(impl: ApiInteractorImpl): ApiInteractor
}
