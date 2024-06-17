package com.innoprog.android.feature.feed.anyProjectDetails.presentation

import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.AnyProjectDetailsModel
import com.innoprog.android.util.ErrorType

sealed interface AnyProjectDetailsScreenState {
    data object Loading : AnyProjectDetailsScreenState

    class Error(val type: ErrorType) : AnyProjectDetailsScreenState

    class Content(
        val anyProjectDetails: AnyProjectDetailsModel
    ) : AnyProjectDetailsScreenState
}
