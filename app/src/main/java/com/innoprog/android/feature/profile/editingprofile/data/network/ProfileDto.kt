package com.innoprog.android.feature.profile.editingprofile.data.network

data class ProfileDto(
    val body: BodyDto,
    val userId: String
)

data class BodyDto(
    val about: String,
    val name: String
)