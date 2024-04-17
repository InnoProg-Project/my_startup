package com.innoprog.android.feature.auth.authorization.data

import com.innoprog.android.network.data.Response


interface NetworkClient {
    suspend fun authorize(dto: AuthorizationBody): Response
}