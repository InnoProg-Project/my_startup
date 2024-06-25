package com.innoprog.android.feature.feed.newsdetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel

data class CommentDto(
    @SerializedName("id")
    val commentId: String,
    @SerializedName("publicationId")
    val publicationId: String,
    @SerializedName("author")
    val commentAuthor: AuthorDto,
    @SerializedName("content")
    val commentContent: String,
    @SerializedName("createdAt")
    val commentCreationDate: String
)

data class AuthorDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)

fun CommentDto.mapToCommentModel(): CommentModel {
    return CommentModel(
        commentId = commentId,
        publicationId = publicationId,
        commentAuthor = commentAuthor.name,
        commentContent = commentContent,
        commentCreationDate = commentCreationDate.substring(0, commentCreationDate.indexOf('T'))
    )
}
