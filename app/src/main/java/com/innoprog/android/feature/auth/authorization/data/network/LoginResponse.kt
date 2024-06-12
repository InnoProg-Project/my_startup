package com.innoprog.android.feature.auth.authorization.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.Response

data class LoginResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
)
