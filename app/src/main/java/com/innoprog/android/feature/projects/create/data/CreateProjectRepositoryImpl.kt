package com.innoprog.android.feature.projects.create.data

import com.innoprog.android.feature.projects.create.domain.CreateProjectRepository
import javax.inject.Inject

class CreateProjectRepositoryImpl @Inject constructor(private val api: CreateProjectApi) :
    CreateProjectRepository {

    private val body: ProjectBody? = null
    override suspend fun createProject() {
        if (body != null) {
            api.setNewProject(body)
        }
    }
}
