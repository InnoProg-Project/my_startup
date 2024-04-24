package com.innoprog.android.feature.profile.profiledetails.di

import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileViewModel
import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.profiledetails.data.impl.ProfileInfoRepoImpl
import com.innoprog.android.feature.profile.profiledetails.data.network.NetworkClient
import com.innoprog.android.feature.profile.profiledetails.data.network.RetrofitNetworkClient
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.impl.GetProfileUseCaseImpl
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
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
    fun bindRepository(impl: ProfileInfoRepoImpl): ProfileInfoRepo

    @Binds
    fun provideGetProfileUseCase(impl: GetProfileUseCaseImpl): GetProfileUseCase

    @Binds
    fun provideNetworkClient(impl: RetrofitNetworkClient): NetworkClient

}

