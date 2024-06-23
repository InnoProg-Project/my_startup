package com.innoprog.android.feature.feed.newsfeed.data.network

import retrofit2.Response
import retrofit2.http.GET

interface FeedApi {
    @GET("/v1/feed")
    suspend fun loadNewsFeed(): Response<List<NewsDto>>
}
