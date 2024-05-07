package com.innoprog.android.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences =
        context.getSharedPreferences("PREFERENCE_INNOPROG",Context.MODE_PRIVATE)
}