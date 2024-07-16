package com.innoprog.android.feature.feed.newsfeed.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("/v1/projects/{projectId}")
    suspend fun getProjectDetails(@Path("projectId") projectId: String): Response<ProjectDto>

    @Suppress("LongParameterList")
    @GET("/v1/feed")
    suspend fun loadNewsFeedQueryPage(
        @Query("type") type: String?,
        @Query("query") query: String?,
        @Query("lastId") lastId: String?,
        @Query("projectId") projectId: String?,
        @Query("authorId") authorId: String?,
        @Query("pageSize") pageSize: Int?,
    ): Response<List<NewsDto>>
}
