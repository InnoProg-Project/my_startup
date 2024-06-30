package com.innoprog.android.feature.training.courseInformation.domain.model

data class CourseInformation(
    val id: String,
    val title: String,
    val direction: String,
    val description: String,
    val authorName: String,
    val createdDate: String,
    val attachments: List<Attachments>,
    //val courseLogoURL: String,
    //val courseAuthorPosition: String,
    //val courseAuthorAvatarURL: String,

)
