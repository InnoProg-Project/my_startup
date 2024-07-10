package com.innoprog.android.feature.projects.projectsScreen.domain.api

import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.util.Resource

interface GetProjectListUseCase {
    suspend fun execute(): Resource<List<Project>>
}
