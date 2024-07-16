package com.innoprog.android.feature.feed.anyProjectDetails.domain.models

sealed interface AnyProjectDetailResponseState {
    data object Loading : AnyProjectDetailResponseState
    class Content(val anyProjectDetails: AnyProjectDetailsModel) :
        AnyProjectDetailResponseState
    data object ConnectionError : AnyProjectDetailResponseState
}
