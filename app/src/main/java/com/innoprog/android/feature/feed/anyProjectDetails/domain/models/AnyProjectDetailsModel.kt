package com.innoprog.android.feature.feed.anyProjectDetails.domain.models

import com.google.gson.annotations.SerializedName

data class AnyProjectDetailsModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageModel>,
    @SerializedName("name")
    val name: String,
    @SerializedName("projectLogoFilePath")
    val projectLogoFilePath: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("documents")
    val documents: List<DocumentModel>,
    @SerializedName("financingStage")
    val financingStage: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("siteUrls")
    val siteUrls: String,
)
