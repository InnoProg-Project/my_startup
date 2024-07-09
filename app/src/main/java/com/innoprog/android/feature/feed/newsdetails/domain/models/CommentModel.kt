package com.innoprog.android.feature.feed.newsdetails.domain.models

class CommentModel(
    val commentId: String,
    val publicationId: String,
    val commentAuthor: CommentAuthor,
    val commentContent: String,
    val commentCreationDate: String,
    var isClicked: Boolean = false
)

data class CommentAuthor(
    val id: String,
    val name: String
)
