package com.innoprog.android.feature.auth.authorization.data

data class LoginResponse(
    val id: String,
    val name: String,
    val authorities: List<String>
)
