package com.innoprog.android.network.domain

interface AuthorizationDataRepository {
    fun execute(): String

    fun loadCredentials(): Pair<String, String>?

    fun setData(login: String, password: String)

    fun checkLastLoginTime()
}
