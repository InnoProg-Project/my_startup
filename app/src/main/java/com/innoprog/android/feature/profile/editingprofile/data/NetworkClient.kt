package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.network.data.Response


interface NetworkClient {
    suspend fun editProfile(): Response
}
