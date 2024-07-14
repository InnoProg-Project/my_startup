package com.innoprog.android.feature.feed.newsdetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.Response

class CommentDto(
    @SerializedName("id")
    val commentId: String,
    @SerializedName("publicationId")
    val publicationId: String,
    @SerializedName("author")
    val commentAuthor: CommentAuthorDto,
    @SerializedName("content")
    val commentContent: String,
    @SerializedName("createdAt")
    val commentCreationDate: String
) : Response()

class CommentAuthorDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)
