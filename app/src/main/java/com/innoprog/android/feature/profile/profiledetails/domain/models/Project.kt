package com.innoprog.android.feature.profile.profiledetails.domain.models

data class Project(
    val id: String,
    val name: String,
    val shortDescription: String,
    val description: String,
    val logoFilePath: String,
    val publicationsCount: Int,
    val area: String,
    val financingStage: String,
    val deadline: String,
    val siteUrls: String,
    val documentUrls: List<String>,
    val projectAttachments: List<ProjectAttachment>
) {
    data class ProjectAttachment(
        val id: String,
        val filePath: String,
        val type: String
    )
}
