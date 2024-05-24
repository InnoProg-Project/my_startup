package com.innoprog.android.feature.auth.registration.data

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.Response

data class RegistrationResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Response()
