package com.innoprog.android.feature.feed.anyProjectDetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.ImageModel
import com.innoprog.android.feature.feed.newsfeed.data.network.Attachment

data class ProjectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<ImageModel>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("logoFilePath")
    val projectLogoFilePath: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("documentUrls")
    val documentUrls: List<String>?,
    @SerializedName("documents")
    val documents: List<DocumentModel>?,
    @SerializedName("financingStage")
    val financingStage: String,
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("siteUrls")
    val siteUrls: List<String>,
    @SerializedName("publicationsCount")
    val publicationsCount: Int,
    @SerializedName("projectAttachments")
    val projectAttachments: List<Attachment>,
)

data class Attachment(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)


fun ProjectDto.mapToDomain(): AnyProjectDetailsModel {
    //return createAndFillProperties(this, AnyProjectDetailsModel::class)
    return AnyProjectDetailsModel(
        id = id,
        images = images,
        name = name,
        projectLogoFilePath = projectLogoFilePath,
        area = area,
        shortDescription = shortDescription,
        description = description,
        documents = listDocumentUrlsToListDocumentModel(documentUrls),
        financingStage = financingStage,
        deadline = deadline,
        siteUrls = siteUrls
    )
}

fun listDocumentUrlsToListDocumentModel(documentUrls: List<String>?): List<DocumentModel>? {
    if (documentUrls == null) return null

    val result = mutableListOf<DocumentModel>()

    documentUrls.forEach {
        result.add(
            DocumentModel(
                documentURL = it,
                documentTitle = it
            )
        )
    }

    return result
}
