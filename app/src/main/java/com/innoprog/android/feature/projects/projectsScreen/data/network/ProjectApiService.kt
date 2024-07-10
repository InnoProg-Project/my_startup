package com.innoprog.android.feature.projects.projectsScreen.data.network

import com.innoprog.android.feature.projects.data.dto.ProjectDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectApiService {
    @GET("/v1/projects")
    suspend fun getProjectList(
        @Query("userId") userId: String,
        @Query("lastId") lastId: String?,
        @Query("pageSize") pageSize: Int
    ): Response<List<ProjectDto>>
}