package com.innoprog.android.feature.feed.newsdetails.data.network

import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsDetailsApi {
    @GET("/v1/feed/{publicationId}")
    suspend fun getNewsDetails(@Path("publicationId") publicationId: String): Response<NewsDto>

    @GET("/v1/feed/{publicationId}/comments")
    suspend fun getComments(@Path("publicationId") publicationId: String): Response<List<CommentDto>>

    @GET("/v1/projects/{projectId}")
    suspend fun getProjectDetails(@Path("projectId") projectId: String): Response<ProjectDto>

    @POST("/v1/feed/{publicationId}/comments")
    suspend fun addComment(
        @Path("publicationId") publicationId: String,
        @Body requestBody: AddCommentRequest
    ): Response<CommentDto>
}
