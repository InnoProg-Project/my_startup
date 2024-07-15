package com.innoprog.android.feature.feed.newsdetails.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.network.data.NetworkModel
import com.innoprog.android.network.data.Response

@NetworkModel
class NewsDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("author")
    val author: AuthorDto,
    @SerializedName("projectId")
    val projectId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("likesCount")
    val likesCount: Int,
    @SerializedName("commentsCount")
    val commentsCount: Int,
    @SerializedName("attachments")
    val attachments: List<AttachmentDto>,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("isFavorited")
    val isFavorited: Boolean,
) : Response()

class AuthorDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("company")
    val company: CompanyDto
)

class CompanyDto(
    @SerializedName("name")
    val companyName: String,
    @SerializedName("role")
    val role: String
)

class AttachmentDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)
