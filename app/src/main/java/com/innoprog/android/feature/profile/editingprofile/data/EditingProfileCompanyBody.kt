package com.innoprog.android.feature.profile.editingprofile.data

import com.google.gson.annotations.SerializedName

data class EditingProfileCompanyBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("role")
    val role: String
)
