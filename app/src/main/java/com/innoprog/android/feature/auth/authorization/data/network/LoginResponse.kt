package com.innoprog.android.feature.auth.authorization.data.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
)
