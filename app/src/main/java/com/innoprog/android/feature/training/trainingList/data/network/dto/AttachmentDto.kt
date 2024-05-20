package com.innoprog.android.feature.training.trainingList.data.network.dto

import com.google.gson.annotations.SerializedName

class AttachmentDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)