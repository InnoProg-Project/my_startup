package com.innoprog.android.feature.feed.newsdetails.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsDetailsApi {
    @GET("/v1/feed/{publicationId}")
    suspend fun getNewsDetails(@Path("publicationId") publicationId: String): Response<NewsDto>

    @GET("/v1/feed/{publicationId}/comments")
    suspend fun getComments(@Path("publicationId") publicationId: String): Response<List<CommentDto>>
}
