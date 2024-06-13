package com.innoprog.android.feature.feed.projectScreen.domain

import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnyProjectInteractorImpl @Inject constructor(private val repository: AnyProjectRepository) :
    AnyProjectInteractor {
    override fun getAnyProject(id: String): Flow<Resource<AnyProjectModel>> {
        return repository.getAnyProject(id)
    }
}
