package com.innoprog.android.feature.profile.editingprofile.data

import com.innoprog.android.network.data.Response

data class ProfileDto(
    val body: BodyResponse,
    val userId: String
)

data class BodyResponse(
    val about: String,
    val name: String
): Response()