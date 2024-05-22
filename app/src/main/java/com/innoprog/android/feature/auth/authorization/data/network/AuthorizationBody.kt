package com.innoprog.android.feature.auth.authorization.data.network

import com.innoprog.android.network.data.Response

data class AuthorizationBody(
    val username: String,
    val password: String
): Response()
