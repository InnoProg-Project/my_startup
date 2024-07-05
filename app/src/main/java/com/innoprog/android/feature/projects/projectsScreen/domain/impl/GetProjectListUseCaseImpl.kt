package com.innoprog.android.feature.projects.projectsScreen.domain.impl

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
                Resource.Success(resource.data)
            }

            is Resource.Error -> resource
        }
    }
}
