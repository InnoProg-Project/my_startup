package com.innoprog.android.feature.auth.authorization.data.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("authorities")
    val authorities: List<String>
) : Response()
