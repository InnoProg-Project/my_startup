package com.innoprog.android.feature.training.trainingList.data.network.dto

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort

data class CourseShortDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("direction")
    val direction: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("author")
    val author: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("usefulLinks")
    val usefulLinks: String?,
    @SerializedName("attachments")
    val attachments: List<AttachmentDto>
)

fun CourseShortDto.mapToDomain(): CourseShort {
    return CourseShort(
        id = id,
        direction = direction ?: "",
        title = title,
        description = description  ?: "",
        authorName = author,
        createdDate = publishedAt.substring(0, publishedAt.indexOf('T'))
    )
}
