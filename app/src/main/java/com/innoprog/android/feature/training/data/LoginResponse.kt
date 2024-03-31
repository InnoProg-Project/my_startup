package com.innoprog.android.feature.training.data

data class LoginResponse (
    val id: String,
    val name: String,
    val authorities: List<String>
)