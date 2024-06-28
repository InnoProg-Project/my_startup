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
    val coverUrl: List<String>?,
    val title: String,
    val content: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    @ColumnInfo(name = "likes_count")
    val likesCount: Int,
    @ColumnInfo(name = "comments_count")
    val commentsCount: Int,
)

data class AuthorEntity(
    val id: String,
    val name: String,
    val company: CompanyEntity
)

data class CompanyEntity(
    val companyName: String,
    val role: String
)
