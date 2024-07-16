package com.innoprog.android.network.domain.cryptography

import android.content.Context

interface RootChecker {
    fun isDeviceRooted(context: Context): Boolean
}