package com.innoprog.android.feature.projects.create.domain.impl

import com.innoprog.android.feature.projects.create.domain.CreateProjectRepository
import com.innoprog.android.feature.projects.create.domain.CreateProjectUseCase
import javax.inject.Inject

class CreateProjectUseCaseImpl @Inject constructor(val repository: CreateProjectRepository) : CreateProjectUseCase {
    override suspend fun createProject() {
        repository.createProject()
    }

}
