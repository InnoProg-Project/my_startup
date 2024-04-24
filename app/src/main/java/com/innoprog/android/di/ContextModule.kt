package com.innoprog.android.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface ContextModule {
    @Binds
    fun provideContext(app: Application): Context
}
