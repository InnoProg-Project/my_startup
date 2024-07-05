package com.innoprog.android.feature.feed.newsfeed.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApi {
    @GET("/v1/feed")
    suspend fun loadNewsFeed(): Response<List<NewsDto>>

    @GET("/v1/projects/{projectId}")
    suspend fun getProjectDetails(@Path("projectId") projectId: String): Response<ProjectDto>
}
