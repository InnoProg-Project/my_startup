package com.innoprog.android.feature.training.courseInformation.domain.model

data class CourseInformationModel(
    val courseId: Int,
    val courseLogoURL: String,
    val courseTitle: String,
    val courseDescription: String,
    val courseAuthorAvatarURL: String,
    val courseAuthorName: String,
    val courseAuthorPosition: String,
    val courseDate: String,
    val courseDirection: String,
    val videos: List<CourseInformationVideoModel>?,
    val documents: List<CourseInformationDocumentModel>?,
)
