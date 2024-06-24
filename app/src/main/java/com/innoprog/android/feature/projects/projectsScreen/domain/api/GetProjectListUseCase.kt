package com.innoprog.android.feature.projects.projectsScreen.domain.api

import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetProjectListUseCase {
    suspend fun execute(
        userId: String,
        lastId: String?,
        pageSize: Int
    ): Flow<Resource<List<Project>>>
}