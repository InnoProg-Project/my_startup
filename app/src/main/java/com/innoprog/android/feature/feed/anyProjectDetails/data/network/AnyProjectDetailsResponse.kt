package com.innoprog.android.feature.feed.anyProjectDetails.data.network

import com.google.gson.annotations.SerializedName

data class AnyProjectDetailsResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
)
