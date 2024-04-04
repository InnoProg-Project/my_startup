package com.innoprog.android.feature.profile.legaldocuments.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.legaldocuments.presentation.LegalDocumentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LegalDocumentModule {

    @IntoMap
    @ViewModelKey(LegalDocumentViewModel::class)
    @Binds
    fun bindLegalDocumentViewModel(impl: LegalDocumentViewModel): ViewModel
}
