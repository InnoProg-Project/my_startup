package com.innoprog.android.feature.profile.editingprofile.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
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
}
