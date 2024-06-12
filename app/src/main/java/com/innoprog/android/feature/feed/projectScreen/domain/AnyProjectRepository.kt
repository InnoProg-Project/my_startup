package com.innoprog.android.feature.feed.projectScreen.domain

import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnyProjectRepository {
    fun getAnyProject(id: String): Flow<Resource<AnyProjectModel>>
}
