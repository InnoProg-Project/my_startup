package com.innoprog.android.feature.projects.projectsScreen.domain.impl

import com.innoprog.android.feature.projects.domain.api.ProjectRepository
import com.innoprog.android.feature.projects.domain.models.Project
import com.innoprog.android.feature.projects.projectsScreen.domain.api.GetProjectListUseCase
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProjectListUseCaseImpl @Inject constructor(
    private val projectRepository: ProjectRepository
) : GetProjectListUseCase {
    override suspend fun execute(
        userId: String,
        lastId: String?,
        pageSize: Int
    ): Flow<Resource<List<Project>>> {
        return flow {
            emit(projectRepository.getProjects(userId, lastId, pageSize))
        }
    }
}
