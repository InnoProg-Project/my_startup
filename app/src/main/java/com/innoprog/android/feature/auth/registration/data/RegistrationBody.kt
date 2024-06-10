package com.innoprog.android.feature.auth.registration.data

import com.google.gson.annotations.SerializedName

data class RegistrationBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("password")
    val password: String,
)
