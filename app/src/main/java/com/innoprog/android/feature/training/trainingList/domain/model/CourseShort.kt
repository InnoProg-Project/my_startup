package com.innoprog.android.feature.training.trainingList.domain.model

import com.innoprog.android.feature.training.trainingList.presentation.model.CoursesItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CourseShort(
    val id: String,
    val direction: String,
    val title: String,
    val description: String,
    val authorName: String,
    val createdDate: String,
)

fun CourseShort.mapToUI(): CoursesItem {
    var resultDate = ""
    runCatching {
        val date = LocalDate.parse(createdDate, DateTimeFormatter.ISO_DATE)
        resultDate = date.format(DateTimeFormatter.ofPattern("dd MMM"))
    }
    return CoursesItem(
        id = id,
        direction = direction,
        title = title,
        description = description,
        authorName = authorName,
        createdDate = resultDate
    )
}