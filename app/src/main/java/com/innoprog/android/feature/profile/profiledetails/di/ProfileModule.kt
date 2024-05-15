package com.innoprog.android.feature.profile.profiledetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.profiledetails.data.impl.ProfileInfoRepoImpl
import com.innoprog.android.feature.profile.profiledetails.data.network.ProfileApi
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileCompanyUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.GetProfileUseCase
import com.innoprog.android.feature.profile.profiledetails.domain.ProfileInfoRepo
import com.innoprog.android.feature.profile.profiledetails.domain.impl.GetProfileCompanyUseCaseImpl
import com.innoprog.android.feature.profile.profiledetails.domain.impl.GetProfileUseCaseImpl
import com.innoprog.android.feature.profile.profiledetails.presentation.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(
    includes = [ProfileModule.ProfileInfoApiModule::class]
)
interface ProfileModule {

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(impl: ProfileViewModel): ViewModel

    @Binds
    fun bindRepository(impl: ProfileInfoRepoImpl): ProfileInfoRepo

    @Binds
    fun bindGetProfileUseCase(impl: GetProfileUseCaseImpl): GetProfileUseCase

    @Binds
    fun bindGetProfileCompanyUseCase(impl: GetProfileCompanyUseCaseImpl): GetProfileCompanyUseCase

    @Module
    class ProfileInfoApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): ProfileApi {
            return retrofit.create(ProfileApi::class.java)
        }
    }
}
