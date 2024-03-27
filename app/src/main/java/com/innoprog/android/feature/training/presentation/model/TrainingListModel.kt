package com.innoprog.android.feature.training.presentation.model

data class TrainingListModel(
    val trainingDirection: String,
    val trainingTitle: String,
    val trainingDescription: String,
    val trainingAuthorAvatarURL: String,
    val trainingAuthorName: String,
    val trainingAuthorPosition: String,
    val trainingDate: String,
)
