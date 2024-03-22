package com.innoprog.android.feature.auth.passwordrecovery.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.passwordrecovery.presentation.PasswordRecoveryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PasswordRecoveryModule {

    @IntoMap
    @ViewModelKey(PasswordRecoveryViewModel::class)
    @Binds
    fun bindNewPasswordRecoveryViewModel(impl: PasswordRecoveryViewModel): ViewModel
}
