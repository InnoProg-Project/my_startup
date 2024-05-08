package com.innoprog.android.feature.auth.authorization.data.network

interface NetworkClient {
    suspend fun authorize(dto: AuthorizationBody): Response
}
