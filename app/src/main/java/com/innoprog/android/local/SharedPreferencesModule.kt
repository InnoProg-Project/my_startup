package com.innoprog.android.local

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
class SharedPreferencesModule {
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences =
        context.getSharedPreferences("PREFERENCE_INNOPROG", Context.MODE_PRIVATE)
}