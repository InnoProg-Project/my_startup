package com.innoprog.android.feature.feed.news_feed.domain.models

data class News(
    val id: String,
    val type: String,
    val author: Author,
    val projectId: String?,
    val coverUrl: String?,
    val title: String,
    val content: String,
    val publishedAt: Long,
    val likesCount: Long,
    val commentsCount: Long,
)

data class Author(
    val id: String,
    val avatarUrl: String?,
    val name: String,
    val company: Company
)

data class Company(
    val companyName: String,
    val role: String
)
