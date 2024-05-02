package com.innoprog.android.feature.projects.projectsScreen.domain

import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel
import com.innoprog.android.feature.training.trainingList.domain.ErrorStatus
import kotlinx.coroutines.flow.Flow

interface ProjectsScreenRepository {

    fun getProjects(): Flow<Pair<List<ProjectScreenModel>?, ErrorStatus?>>
}
