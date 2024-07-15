package com.innoprog.android.feature.feed.anyProjectDetails.domain

import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnyProjectDetailsInteractorImpl @Inject constructor(private val repository: AnyProjectDetailsRepository) :
    AnyProjectDetailsInteractor {
    override fun getAnyProjectDetails(id: String): Flow<Resource<AnyProjectDetailsModel>> {
        return repository.getAnyProjectDetails(id)
    }
}
