package com.innoprog.android.feature.projects.projectsScreen.domain.useCase

import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface GetProjectsUseCase {

    fun execute(): Flow<Pair<List<ProjectScreenModel>?, ErrorStatus?>>
}
