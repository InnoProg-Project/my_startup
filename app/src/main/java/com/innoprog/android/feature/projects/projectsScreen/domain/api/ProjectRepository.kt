package com.innoprog.android.feature.projects.domain.api

import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.util.Resource

interface ProjectRepository {
    suspend fun getProjects(
        userId: String,
        lastId: String?,
        pageSize: Int
    ): Resource<List<Project>>
}