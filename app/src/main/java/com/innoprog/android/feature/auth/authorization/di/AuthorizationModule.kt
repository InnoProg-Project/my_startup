package com.innoprog.android.feature.auth.authorization.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.authorization.presentation.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthorizationModule {

    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    @Binds
    fun bindAuthorizationViewModel(impl: AuthorizationViewModel): ViewModel
}
