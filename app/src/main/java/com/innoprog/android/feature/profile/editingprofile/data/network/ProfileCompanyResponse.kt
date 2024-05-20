package com.innoprog.android.feature.profile.editingprofile.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.Response

data class ProfileCompanyResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("role")
    val role: String
) : Response()
