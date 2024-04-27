package com.innoprog.android.feature.profile.editingprofile.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.editingprofile.data.ProfileRepositoryImpl
import com.innoprog.android.feature.profile.editingprofile.data.NetworkClient
import com.innoprog.android.feature.profile.editingprofile.data.RetrofitNetworkClient
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileRepository
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileUseCase
import com.innoprog.android.feature.profile.editingprofile.domain.ProfileUseCaseImpl
import com.innoprog.android.feature.profile.editingprofile.presentation.EditingProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EditingProfileModule {

    @IntoMap
    @ViewModelKey(EditingProfileViewModel::class)
    @Binds
    fun bindEditingProfileViewModel(impl: EditingProfileViewModel): ViewModel

    @Binds
    fun provideNetworkClient(impl: RetrofitNetworkClient): NetworkClient
    @Binds
    fun bindProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindProfileUseCase(useCase: ProfileUseCaseImpl): ProfileUseCase
}
