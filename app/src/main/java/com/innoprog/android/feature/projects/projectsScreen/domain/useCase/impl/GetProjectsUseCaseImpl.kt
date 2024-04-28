package com.innoprog.android.feature.projects.projectsScreen.domain.useCase.impl

import com.innoprog.android.feature.projects.projectsScreen.domain.ProjectsScreenRepository
import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel
import com.innoprog.android.feature.projects.projectsScreen.domain.useCase.GetProjectsUseCase
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectsScreenRepository,
) : GetProjectsUseCase {

    override fun execute(): Flow<Pair<List<ProjectScreenModel>?, ErrorStatus?>> {
        return repository.getProjects()
    }
}
