package com.innoprog.android.feature.profile.profiledetails.data.network

import com.innoprog.android.network.data.Response

interface NetworkClient {
    suspend fun getProfile(): Response
    suspend fun getProfileCompany(): Response
}
