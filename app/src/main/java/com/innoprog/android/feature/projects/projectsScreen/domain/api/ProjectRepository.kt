package com.innoprog.android.feature.projects.projectsScreen.domain.api

import com.innoprog.android.feature.projects.data.dto.ProjectDto
import com.innoprog.android.util.Resource

interface ProjectRepository {
    suspend fun getProjectList(): Resource<List<ProjectDto>>
}
