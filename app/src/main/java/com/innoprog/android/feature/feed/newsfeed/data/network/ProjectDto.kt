package com.innoprog.android.feature.feed.newsfeed.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.NetworkModel
import com.innoprog.android.network.data.Response

@NetworkModel
class ProjectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("logoFilePath")
    val logoFilePath: String,
    @SerializedName("publicationsCount")
    val publicationsCount: Int,
    @SerializedName("area")
    val area: String,
    @SerializedName("financingStage")
    val financingStage: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("siteUrls")
    val siteUrls: String,
    @SerializedName("documentUrls")
    val documentUrls: List<String>,
    @SerializedName("projectAttachments")
    val projectAttachments: List<Attachment>,
) : Response()

class Attachment(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)
