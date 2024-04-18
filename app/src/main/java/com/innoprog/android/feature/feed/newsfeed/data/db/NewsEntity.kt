package com.innoprog.android.feature.feed.newsfeed.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    @Embedded(prefix = "author_")
    val author: AuthorEntity,
    @ColumnInfo(name = "project_id")
    val projectId: String?,
    @ColumnInfo(name = "cover_url")
    val coverUrl: String?,
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
    val id: String,
    val avatarUrl: String?,
    val name: String,
    val company: CompanyEntity
)

data class CompanyEntity(
    val companyName: String,
    val role: String
)
