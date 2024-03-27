package com.innoprog.android.feature.training.trainingList.presentation.model

data class TrainingListModel(
    val trainingId: Int,
    val trainingDirection: String,
    val trainingTitle: String,
    val trainingDescription: String,
    val trainingAuthorAvatarURL: String,
    val trainingAuthorName: String,
    val trainingAuthorPosition: String,
    val trainingDate: String,
)
