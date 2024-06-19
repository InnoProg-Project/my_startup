package com.innoprog.android.feature.profile.editingprofile.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.editingprofile.data.EditingProfileApi
import com.innoprog.android.feature.profile.editingprofile.data.impl.EditProfileInfoRepoImpl
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileInfoRepo
import com.innoprog.android.feature.profile.editingprofile.domain.EditProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.impl.EditProfileCompanyUseCaseImpl
import com.innoprog.android.feature.profile.editingprofile.domain.impl.EditProfileUseCaseImpl
import com.innoprog.android.feature.profile.editingprofile.presentation.EditingProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
interface EditingProfileModule {

    @IntoMap
    @ViewModelKey(EditingProfileViewModel::class)
    @Binds
    fun bindEditingProfileViewModel(impl: EditingProfileViewModel): ViewModel

    @Binds
    fun bindRepository(impl: EditProfileInfoRepoImpl): EditProfileInfoRepo

    @Binds
    fun bindEditProfileUseCase(impl: EditProfileUseCaseImpl): EditProfileUseCase

    @Binds
    fun bindEditProfileCompanyUseCase(impl: EditProfileCompanyUseCaseImpl): EditProfileCompanyUseCase

    @Module
    class EditProfileApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): EditingProfileApi {
            return retrofit.create(EditingProfileApi::class.java)
        }
    }
}
