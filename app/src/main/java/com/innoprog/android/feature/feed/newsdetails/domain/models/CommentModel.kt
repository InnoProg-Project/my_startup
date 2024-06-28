package com.innoprog.android.feature.feed.newsdetails.domain.models

data class CommentModel(
    val commentId: String,
    val publicationId: String,
    val commentAuthor: CommentAuthor,
    val commentContent: String,
    val commentCreationDate: String
)

data class CommentAuthor(
    val id: String,
    val name: String
)
