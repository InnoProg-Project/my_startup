package com.innoprog.android.feature.auth.authorization.data

import android.content.SharedPreferences
import javax.inject.Inject

class AuthSharedPReferencesLocalStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthLocalStorage {

    override fun setCookies(headers: String) {
        sharedPreferences.edit()
            .putString(COOKIE_KEY, headers)
            .apply()
    }

    private companion object {
        private const val COOKIE_KEY = "COOKIE_KEY"
    }
}
