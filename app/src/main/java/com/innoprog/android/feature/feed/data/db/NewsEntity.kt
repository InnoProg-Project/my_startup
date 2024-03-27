package com.innoprog.android.feature.feed.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val author: AuthorEntity,
    @ColumnInfo(name = "project_id")
    val projectId: String?,
    val title: String,
    val content: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: Long,
    @ColumnInfo(name = "likes_count")
    val likesCount: Long,
    @ColumnInfo(name = "comments_count")
    val commentsCount: Long,
)

data class AuthorEntity(
    val authorId: String,
    val authorName: String,
    val company: CompanyEntity
)

data class CompanyEntity(
    val companyName: String,
    val role: String
)
