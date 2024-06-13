package com.innoprog.android.feature.auth.registration.data

import com.google.gson.annotations.SerializedName

class RegistrationResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
