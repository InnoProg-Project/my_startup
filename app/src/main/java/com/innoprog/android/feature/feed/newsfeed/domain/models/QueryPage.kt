package com.innoprog.android.feature.feed.newsfeed.domain.models

data class QueryPage(
    val nextPageNumber: Int? = null,
    val lastId: String? = null,
    val query: String? = null,
    val pageSize: Int? = null,
    val type: String? = null,
    val projectId: String? = null,
    val authorId: String? = null
)
