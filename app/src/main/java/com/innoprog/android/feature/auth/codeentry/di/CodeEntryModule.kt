package com.innoprog.android.feature.auth.codeentry.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.auth.codeentry.presentation.CodeEntryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CodeEntryModule {

    @IntoMap
    @ViewModelKey(CodeEntryViewModel::class)
    @Binds
    fun bindCodeEntryViewModel(impl: CodeEntryViewModel): ViewModel
}
