package com.innoprog.android.local

import dagger.Binds
import dagger.Module

@Module
interface LocalStorageModule {
    @Binds
    fun bindSharedPrefLocalStorage(storage: SharedPreferencesLocalStorageImpl): LocalStorage
}