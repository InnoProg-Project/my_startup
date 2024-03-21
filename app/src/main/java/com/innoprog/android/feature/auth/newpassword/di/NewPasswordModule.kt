package com.innoprog.android.feature.auth.newpassword.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.newpassword.presentation.NewPasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewPasswordModule {

    @IntoMap
    @ViewModelKey(NewPasswordViewModel::class)
    @Binds
    fun bindNewPasswordViewModel(impl: NewPasswordViewModel): ViewModel
}
