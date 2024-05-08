package com.innoprog.android.feature.auth.authorization.data.network

data class AuthorizationBody(
    val username: String,
    val password: String
) : Response()
