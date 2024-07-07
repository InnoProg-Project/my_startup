package com.innoprog.android.feature.training.courseInformation.domain.model

data class CourseInformation(
    val id: String,
    val title: String,
    val direction: String,
    val description: String,
    val authorName: String,
    val createdDate: String,
    val usefulLinks: String?,
    val videoList: List<String>,
    val documentList: List<String>,
    val imageList: List<String>
)
