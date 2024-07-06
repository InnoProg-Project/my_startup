package com.innoprog.android.feature.feed.anyProjectDetails.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnyProjectDetailsApi {
    @GET("/v1/projects/{projectId}")
    suspend fun getProjectById(@Path("projectId") id: String): Response<AnyProjectDetailsResponse>
}
