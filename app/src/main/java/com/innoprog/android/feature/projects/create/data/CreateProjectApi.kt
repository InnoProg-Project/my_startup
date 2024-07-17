package com.innoprog.android.feature.projects.create.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateProjectApi {
    @POST("/v1/projects")
    suspend fun setNewProject(@Body body: ProjectBody): Response<CreateResponse>
}
