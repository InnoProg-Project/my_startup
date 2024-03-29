package com.innoprog.android.feature.profile.editingprofile.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [EditingProfileModule::class]
)
interface EditingProfileComponent : ScreenComponent
