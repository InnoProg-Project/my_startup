package com.innoprog.android.network.data

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
