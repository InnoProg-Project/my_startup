package com.innoprog.android.feature.profile.editingprofile.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.editingprofile.data.impl.ProfileRepositoryImpl
import com.innoprog.android.feature.profile.editingprofile.data.network.ProfileApi
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileCompanyUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.SaveProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.impl.SaveProfileCompanyUseCaseImpl
import com.innoprog.android.feature.profile.editingprofile.domain.impl.SaveProfileUseCaseImpl
import com.innoprog.android.feature.profile.editingprofile.presentation.EditingProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(
    includes = [EditingProfileModule.ProfileInfoApiModule::class]
)
interface EditingProfileModule {

    @IntoMap
    @ViewModelKey(EditingProfileViewModel::class)
    @Binds
    fun bindEditingProfileViewModel(impl: EditingProfileViewModel): ViewModel

    @Binds
    fun bindRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindSaveProfileUseCase(impl: SaveProfileUseCaseImpl): SaveProfileUseCase

    @Binds
    fun bindSaveProfileCompanyUseCase(impl: SaveProfileCompanyUseCaseImpl): SaveProfileCompanyUseCase

    @Module
    class ProfileInfoApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): ProfileApi {
            return retrofit.create(ProfileApi::class.java)
        }
    }
}
