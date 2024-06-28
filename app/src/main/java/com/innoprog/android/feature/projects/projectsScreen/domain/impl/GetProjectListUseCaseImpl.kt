package com.innoprog.android.feature.projects.projectsScreen.domain.impl

import com.innoprog.android.feature.projects.data.converter.convert
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.feature.projects.projectsScreen.domain.api.ProjectRepository
import com.innoprog.android.util.Resource
import javax.inject.Inject

class GetProjectListUseCaseImpl @Inject constructor(
    private val projectRepository: ProjectRepository
) : GetProjectListUseCase {
    override suspend fun execute(): Resource<List<Project>> {
        return when (val resource = projectRepository.getProjectList()) {
            is Resource.Success -> {
                val projects = resource.data.map { it.convert() }
                Resource.Success(projects)
            }

            is Resource.Error -> resource
        }
    }
}
