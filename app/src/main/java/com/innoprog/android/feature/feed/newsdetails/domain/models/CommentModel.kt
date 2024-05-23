package com.innoprog.android.feature.feed.newsdetails.domain.models

data class CommentModel(
    val commentId: String,
    val publicationId: String,
    val commentAuthor: String,
    val commentContent: String,
    val commentCreationDate: String
)
