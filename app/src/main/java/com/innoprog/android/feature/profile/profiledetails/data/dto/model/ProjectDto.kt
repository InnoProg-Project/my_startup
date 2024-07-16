package com.innoprog.android.feature.profile.profiledetails.data.dto.model

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.network.data.NetworkModel
import com.innoprog.android.network.data.Response

@NetworkModel
class ProjectDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("logoFilePath")
    val logoUrl: String
) : Response()

fun ProjectDto.mapToDomain(): Project {
    return Project(
        id = id,
        name = name,
        area = area,
        logoUrl = logoUrl
    )
}