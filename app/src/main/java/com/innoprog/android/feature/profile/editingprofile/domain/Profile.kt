package com.innoprog.android.feature.profile.editingprofile.domain

data class Profile(
    val body: Body,
    val userId: String
)

data class Body(
    val about: String,
    val name: String
)