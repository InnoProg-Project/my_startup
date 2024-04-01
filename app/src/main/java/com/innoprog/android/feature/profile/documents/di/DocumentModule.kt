package com.innoprog.android.feature.profile.documents.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.profile.documents.presentation.DocumentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DocumentModule {

    @IntoMap
    @ViewModelKey(DocumentViewModel::class)
    @Binds
    fun bindDocumentViewModel(impl: DocumentViewModel): ViewModel
}
