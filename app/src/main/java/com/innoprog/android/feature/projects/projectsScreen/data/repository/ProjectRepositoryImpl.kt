package com.innoprog.android.feature.projects.projectsScreen.data.repository

import com.innoprog.android.feature.projects.projectsScreen.data.api.ProjectApiService
import com.innoprog.android.feature.projects.data.converter.convert
import com.innoprog.android.feature.projects.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.network.data.toResource
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectApiService: ProjectApiService
) : ProjectRepository {
    override suspend fun getProjects(
        userId: String,
        lastId: String?,
        pageSize: Int
    ): Resource<List<Project>> {
        return try {
            val response = projectApiService.getProjectList(userId, lastId, pageSize)
            val resource = response.toResource()
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.map { it.convert() })
                is Resource.Error -> resource
            }
        } catch (e: Exception) {
            Resource.Error(ErrorType.UNKNOWN_ERROR)
        }
    }
}
