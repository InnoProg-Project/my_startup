package com.innoprog.android.feature.edit.data.dto

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.Response

data class ProjectResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("logoFilePath")
    val logoFilePath: String,
) : Response()
