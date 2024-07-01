package com.innoprog.android.feature.training.courseInformation.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.training.courseInformation.domain.model.Attachments
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CourseInformationDto(
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
    val attachments: List<AttachmentsDto>
)

class AttachmentsDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)

fun CourseInformationDto.mapToCourseInformation(): CourseInformation {
    return CourseInformation(
        id = id,
        title = title,
        direction = direction ?: "",
        description = description ?: "",
        authorName = author,
        createdDate = formatDate(publishedAt),
        attachments = attachments.map {
            Attachments(
                id = it.id,
                filePath = it.filePath,
                type = it.type
            )
        }
    )
}

fun formatDate(publishedAt: String): String {
    var resultDate = ""
    val createdDate = publishedAt.substring(0, publishedAt.indexOf('T'))
    runCatching {
        val date = LocalDate.parse(createdDate, DateTimeFormatter.ISO_DATE)
        resultDate = date.format(DateTimeFormatter.ofPattern("dd MMM"))
    }
    return resultDate
}