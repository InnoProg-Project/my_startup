package com.innoprog.android.feature.feed.projectScreen.domain

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject

data class AnyProjectModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("logoFilePath")
    val logoFilePath: String,
    @SerializedName("publicationsCount")
    val publicationsCount: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("projectNews")
    val projectNews: List<NewsWithProject>?
)
