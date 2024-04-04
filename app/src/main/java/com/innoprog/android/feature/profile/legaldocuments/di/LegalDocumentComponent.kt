package com.innoprog.android.feature.profile.legaldocuments.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [LegalDocumentModule::class]
)
interface LegalDocumentComponent : ScreenComponent
