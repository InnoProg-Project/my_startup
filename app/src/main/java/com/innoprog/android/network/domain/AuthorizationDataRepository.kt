package com.innoprog.android.network.domain

interface AuthorizationDataRepository {
    fun execute(): String

    fun setData(login: String, password: String)
}
