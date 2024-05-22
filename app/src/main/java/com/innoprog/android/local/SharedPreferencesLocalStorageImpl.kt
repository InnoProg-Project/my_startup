package com.innoprog.android.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class SharedPreferencesLocalStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LocalStorage {

    override var headers: ArrayList<String>
        get() {
            val json = sharedPreferences.getString(COOKIE_KEY, null) ?:  return arrayListOf()
            val listOfObjects: Type = object : TypeToken<ArrayList<String>?>() {}.type
            return Gson().fromJson(json, listOfObjects)
        }
        set(headers) {
            val json = Gson().toJson(headers)
            sharedPreferences.edit()
                .putString(COOKIE_KEY, json)
                .apply()
        }


    private companion object {
        private const val COOKIE_KEY = "COOKIE_KEY"
    }
}
