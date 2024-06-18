package com.innoprog.android.feature.feed.anyProjectDetails.domain

import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnyProjectDetailsInteractor {
    fun getAnyProjectDetails(id: String): Flow<Resource<AnyProjectDetailsModel>>
}
