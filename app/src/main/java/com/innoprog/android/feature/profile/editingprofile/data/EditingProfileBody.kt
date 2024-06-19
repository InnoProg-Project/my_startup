package com.innoprog.android.feature.profile.editingprofile.data

import com.google.gson.annotations.SerializedName

data class EditingProfileBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("about")
    val about: String,
)
