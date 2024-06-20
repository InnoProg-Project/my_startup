package com.innoprog.android.feature.edit.data.dto

import com.google.gson.annotations.SerializedName

data class ProjectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("logoFilePath")
    val logoFilePath: String,
)
